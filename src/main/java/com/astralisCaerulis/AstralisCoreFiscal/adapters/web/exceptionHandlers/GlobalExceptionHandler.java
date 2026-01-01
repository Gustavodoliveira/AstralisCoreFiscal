package com.astralisCaerulis.AstralisCoreFiscal.adapters.web.exceptionHandlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.user.EmailAlreadyExistsException;
import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.user.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
        "error", "User not found",
        "message", ex.getMessage(),
        "status", 404));
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<Map<String, Object>> handleEmailExists(EmailAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of(
            "error", "Email Already Exists",
            "message", ex.getMessage(),
            "status", 409));
  }

  // Race condition / Constraint violation
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of(
            "error", "Data Integrity Violation",
            "message", "Dados duplicados ou violação de restrição",
            "status", 409));
  }

  // Exceção genérica
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of(
            "error", "Internal Server Error",
            "message", "Erro interno do servidor",
            "status", 500));
  }
}
