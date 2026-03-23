package com.astralisCaerulis.AstralisCoreFiscal.adapters.web.exceptionHandlers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.enterprise.CnpjAlreadyExistsException;

@DisplayName("GlobalExceptionHandler - Testes de Exceções Enterprise")
class GlobalExceptionHandlerEnterpriseTest {

  private GlobalExceptionHandler globalExceptionHandler;

  @BeforeEach
  void setUp() {
    globalExceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  @DisplayName("Deve tratar CnpjAlreadyExistsException e retornar status 409")
  void shouldHandleCnpjAlreadyExistsException() {
    // Arrange
    String cnpj = "12.345.678/0001-90";
    String message = "CNPJ already exists: " + cnpj;
    CnpjAlreadyExistsException exception = new CnpjAlreadyExistsException(message);

    // Act
    ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleCnpjExists(exception);

    // Assert
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals("CNPJ Already Exists", body.get("error"));
    assertEquals(message, body.get("message"));
    assertEquals(409, body.get("status"));
  }

  @Test
  @DisplayName("Deve manter consistência na resposta de erro")
  void shouldMaintainErrorResponseConsistency() {
    // Arrange
    String testMessage = "Test CNPJ duplication";
    CnpjAlreadyExistsException exception = new CnpjAlreadyExistsException(testMessage);

    // Act
    ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleCnpjExists(exception);

    // Assert
    Map<String, Object> body = response.getBody();
    assertNotNull(body);

    // Verificar que possui todas as chaves necessárias
    assertTrue(body.containsKey("error"));
    assertTrue(body.containsKey("message"));
    assertTrue(body.containsKey("status"));

    // Verificar tipos corretos
    assertInstanceOf(String.class, body.get("error"));
    assertInstanceOf(String.class, body.get("message"));
    assertInstanceOf(Integer.class, body.get("status"));
  }
}