package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.LoginRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.LoginResponse;
import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.user.InvalidCredentialsException;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.UserRepository;
import com.astralisCaerulis.AstralisCoreFiscal.infrastructure.security.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginUserCase {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public LoginResponse execute(LoginRequest request) {
    try {
      log.info("🔐 Attempting login for email: {}", request.getEmail());

      // 1. Autenticar credenciais
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getEmail(),
              request.getPassword()));

      // 2. Buscar usuário no banco
      User user = userRepository.findByEmail(request.getEmail())
          .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

      // 3. Gerar token JWT
      UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
      String token = jwtService.generateToken(userDetails);

      log.info("✅ Login successful for user: {} (ID: {})", user.getName(), user.getId());

      // 4. Construir resposta
      return new LoginResponse(
          user.getId(),
          user.getName(),
          user.getEmail(),
          user.getPhone(),
          token,
          "Login successful");

    } catch (AuthenticationException e) {
      log.warn("❌ Login failed for email: {} - {}", request.getEmail(), e.getMessage());
      throw new InvalidCredentialsException("Invalid email or password");
    }
  }
}