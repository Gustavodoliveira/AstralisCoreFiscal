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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.user.EmailAlreadyExistsException;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("CreateUserCase - Testes Unitários")
class CreateUserCaseTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private CreateUserCase createUserCase;

  private User validUser;

  @BeforeEach
  void setUp() {
    validUser = new User(
        null,
        "João Silva",
        "joao@example.com",
        "+5511999999999",
        "senha123");
  }

  @Test
  @DisplayName("Deve criar usuário com sucesso")
  void shouldCreateUserSuccessfully() {
    // Arrange
    log.info("🧪 Iniciando teste: Criar usuário com sucesso");
    String hashedPassword = "hashedPassword123";
    User savedUser = new User(
        UUID.randomUUID(),
        validUser.getName(),
        validUser.getEmail(),
        validUser.getPhone(),
        hashedPassword);

    when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(validUser.getPasswordHash())).thenReturn(hashedPassword);
    when(userRepository.save(any(User.class))).thenReturn(savedUser);

    // Act
    User result = createUserCase.execute(validUser);

    // Assert
    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals(validUser.getName(), result.getName());
    assertEquals(validUser.getEmail(), result.getEmail());
    assertEquals(validUser.getPhone(), result.getPhone());
    assertEquals(hashedPassword, result.getPasswordHash());

    verify(userRepository, times(1)).findByEmail(validUser.getEmail());
    verify(passwordEncoder, times(1)).encode(validUser.getPasswordHash());
    verify(userRepository, times(1)).save(any(User.class));

    log.info("✅ Teste concluído com sucesso: Usuário criado - ID: {}", result.getId());
  }

  @Test
  @DisplayName("Deve lançar exceção quando email já existe")
  void shouldThrowExceptionWhenEmailAlreadyExists() {
    // Arrange
    log.info("🧪 Iniciando teste: Email já existe");
    User existingUser = new User(
        UUID.randomUUID(),
        "Usuário Existente",
        validUser.getEmail(),
        "+5511888888888",
        "hashedPassword");

    when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Optional.of(existingUser));

    // Act & Assert
    EmailAlreadyExistsException exception = assertThrows(
        EmailAlreadyExistsException.class,
        () -> createUserCase.execute(validUser));

    assertEquals("Email Already exists: " + validUser.getEmail(), exception.getMessage());

    verify(userRepository, times(1)).findByEmail(validUser.getEmail());
    verify(passwordEncoder, never()).encode(anyString());
    verify(userRepository, never()).save(any(User.class));

    log.info("✅ Teste concluído com sucesso: Exceção lançada corretamente");
  }

  @Test
  @DisplayName("Deve criptografar senha antes de salvar")
  void shouldEncryptPasswordBeforeSaving() {
    // Arrange
    log.info("🧪 Iniciando teste: Criptografia de senha");
    String plainPassword = "senha123";
    String hashedPassword = "$2a$10$hashedPassword";
    User userWithPlainPassword = new User(
        null,
        "Maria Santos",
        "maria@example.com",
        "+5511777777777",
        plainPassword);

    User savedUser = new User(
        UUID.randomUUID(),
        userWithPlainPassword.getName(),
        userWithPlainPassword.getEmail(),
        userWithPlainPassword.getPhone(),
        hashedPassword);

    when(userRepository.findByEmail(userWithPlainPassword.getEmail())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(plainPassword)).thenReturn(hashedPassword);
    when(userRepository.save(any(User.class))).thenReturn(savedUser);

    // Act
    User result = createUserCase.execute(userWithPlainPassword);

    // Assert
    assertNotEquals(plainPassword, result.getPasswordHash());
    assertEquals(hashedPassword, result.getPasswordHash());

    verify(passwordEncoder, times(1)).encode(plainPassword);

    log.info("✅ Teste concluído com sucesso: Senha criptografada corretamente");
  }

  @Test
  @DisplayName("Deve manter dados do usuário após criação")
  void shouldMaintainUserDataAfterCreation() {
    // Arrange
    log.info("🧪 Iniciando teste: Manter dados do usuário");
    User savedUser = new User(
        UUID.randomUUID(),
        validUser.getName(),
        validUser.getEmail(),
        validUser.getPhone(),
        "hashedPassword");

    when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
    when(userRepository.save(any(User.class))).thenReturn(savedUser);

    // Act
    User result = createUserCase.execute(validUser);

    // Assert
    assertEquals(validUser.getName(), result.getName());
    assertEquals(validUser.getEmail(), result.getEmail());
    assertEquals(validUser.getPhone(), result.getPhone());

    log.info("✅ Teste concluído com sucesso: Dados mantidos corretamente");
  }
}
