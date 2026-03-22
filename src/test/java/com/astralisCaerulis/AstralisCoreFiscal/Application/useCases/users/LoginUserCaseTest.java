package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.LoginRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.LoginResponse;
import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.user.InvalidCredentialsException;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.UserRepository;
import com.astralisCaerulis.AstralisCoreFiscal.infrastructure.security.JwtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("LoginUserCase - Testes Unitários")
class LoginUserCaseTest {

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtService jwtService;

  @Mock
  private UserDetailsService userDetailsService;

  @Mock
  private Authentication authentication;

  @Mock
  private UserDetails userDetails;

  @InjectMocks
  private LoginUserCase loginUserCase;

  private LoginRequest validLoginRequest;
  private User validUser;

  @BeforeEach
  void setUp() {
    validLoginRequest = new LoginRequest(
        "joao@example.com",
        "senha123");

    validUser = new User(
        UUID.randomUUID(),
        "João Silva",
        "joao@example.com",
        "+5511999999999",
        "$2a$10$hashedPassword");
  }

  @Test
  @DisplayName("Deve fazer login com sucesso")
  void shouldLoginSuccessfully() {
    // Arrange
    log.info("🧪 Iniciando teste: Login com sucesso");
    String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(userRepository.findByEmail(validLoginRequest.getEmail()))
        .thenReturn(Optional.of(validUser));
    when(userDetailsService.loadUserByUsername(validLoginRequest.getEmail()))
        .thenReturn(userDetails);
    when(jwtService.generateToken(userDetails)).thenReturn(jwtToken);

    // Act
    LoginResponse result = loginUserCase.execute(validLoginRequest);

    // Assert
    assertNotNull(result);
    assertEquals(validUser.getId(), result.getId());
    assertEquals(validUser.getName(), result.getName());
    assertEquals(validUser.getEmail(), result.getEmail());
    assertEquals(validUser.getPhone(), result.getPhone());
    assertEquals(jwtToken, result.getToken());
    assertEquals("Login successful", result.getMessage());

    verify(authenticationManager, times(1))
        .authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userRepository, times(1)).findByEmail(validLoginRequest.getEmail());
    verify(userDetailsService, times(1)).loadUserByUsername(validLoginRequest.getEmail());
    verify(jwtService, times(1)).generateToken(userDetails);

    log.info("✅ Teste concluído com sucesso: Login realizado - Token: {}",
        jwtToken.substring(0, 10) + "...");
  }

  @Test
  @DisplayName("Deve lançar exceção quando credenciais são inválidas")
  void shouldThrowExceptionWhenCredentialsAreInvalid() {
    // Arrange
    log.info("🧪 Iniciando teste: Credenciais inválidas");

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new BadCredentialsException("Bad credentials"));

    // Act & Assert
    InvalidCredentialsException exception = assertThrows(
        InvalidCredentialsException.class,
        () -> loginUserCase.execute(validLoginRequest));

    assertEquals("Invalid email or password", exception.getMessage());

    verify(authenticationManager, times(1))
        .authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userRepository, never()).findByEmail(anyString());
    verify(jwtService, never()).generateToken(any());

    log.info("✅ Teste concluído com sucesso: Exceção lançada corretamente");
  }

  @Test
  @DisplayName("Deve lançar exceção quando usuário não existe")
  void shouldThrowExceptionWhenUserNotFound() {
    // Arrange
    log.info("🧪 Iniciando teste: Usuário não encontrado");

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(userRepository.findByEmail(validLoginRequest.getEmail()))
        .thenReturn(Optional.empty());

    // Act & Assert
    InvalidCredentialsException exception = assertThrows(
        InvalidCredentialsException.class,
        () -> loginUserCase.execute(validLoginRequest));

    assertEquals("Invalid email or password", exception.getMessage());

    verify(authenticationManager, times(1))
        .authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userRepository, times(1)).findByEmail(validLoginRequest.getEmail());
    verify(jwtService, never()).generateToken(any());

    log.info("✅ Teste concluído com sucesso: Exceção lançada corretamente");
  }

  @Test
  @DisplayName("Deve gerar token JWT válido no login")
  void shouldGenerateValidJwtTokenOnLogin() {
    // Arrange
    log.info("🧪 Iniciando teste: Geração de token JWT");
    String expectedToken = "valid.jwt.token";

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(userRepository.findByEmail(validLoginRequest.getEmail()))
        .thenReturn(Optional.of(validUser));
    when(userDetailsService.loadUserByUsername(validLoginRequest.getEmail()))
        .thenReturn(userDetails);
    when(jwtService.generateToken(userDetails)).thenReturn(expectedToken);

    // Act
    LoginResponse result = loginUserCase.execute(validLoginRequest);

    // Assert
    assertNotNull(result.getToken());
    assertEquals(expectedToken, result.getToken());

    verify(jwtService, times(1)).generateToken(userDetails);

    log.info("✅ Teste concluído com sucesso: Token JWT gerado corretamente");
  }
}