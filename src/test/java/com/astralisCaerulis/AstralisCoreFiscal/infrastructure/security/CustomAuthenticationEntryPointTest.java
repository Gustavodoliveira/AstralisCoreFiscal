package com.astralisCaerulis.AstralisCoreFiscal.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomAuthenticationEntryPoint - Testes Unitários")
class CustomAuthenticationEntryPointTest {

  @InjectMocks
  private CustomAuthenticationEntryPoint authenticationEntryPoint;

  private HttpServletRequest request;
  private HttpServletResponse response;
  private AuthenticationException authException;
  private StringWriter stringWriter;
  private PrintWriter printWriter;

  @BeforeEach
  void setUp() throws IOException {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    authException = mock(AuthenticationException.class);

    stringWriter = new StringWriter();
    printWriter = new PrintWriter(stringWriter);

    when(response.getWriter()).thenReturn(printWriter);
    when(request.getRequestURI()).thenReturn("/users/by-id/123");
    when(authException.getMessage()).thenReturn("Authentication required");
  }

  @Test
  @DisplayName("Deve retornar status 401 quando usuário não está autenticado")
  void shouldReturn401WhenUserNotAuthenticated() throws IOException, ServletException {
    // Act
    authenticationEntryPoint.commence(request, response, authException);

    // Assert
    verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    verify(response).setContentType("application/json");
    verify(response).setCharacterEncoding("UTF-8");

    String jsonResponse = stringWriter.toString();
    assertTrue(jsonResponse.contains("\"error\":\"Unauthorized\""));
    assertTrue(jsonResponse.contains("\"message\":\"Authentication required to access this resource\""));
    assertTrue(jsonResponse.contains("\"status\":401"));
    assertTrue(jsonResponse.contains("\"path\":\"/users/by-id/123\""));
  }

  @Test
  @DisplayName("Deve incluir o path correto na resposta")
  void shouldIncludeCorrectPathInResponse() throws IOException, ServletException {
    // Arrange
    when(request.getRequestURI()).thenReturn("/users/login");

    // Act
    authenticationEntryPoint.commence(request, response, authException);

    // Assert
    String jsonResponse = stringWriter.toString();
    assertTrue(jsonResponse.contains("\"path\":\"/users/login\""));
  }
}