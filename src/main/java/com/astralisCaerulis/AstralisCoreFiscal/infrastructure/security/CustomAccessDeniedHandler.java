package com.astralisCaerulis.AstralisCoreFiscal.infrastructure.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {

    log.warn("🔒 Access denied for user to: {} - {}", request.getRequestURI(), accessDeniedException.getMessage());

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    var errorResponse = new ErrorResponse(
        "Access Denied",
        "You don't have permission to access this resource",
        403,
        request.getRequestURI());

    String jsonResponse = objectMapper.writeValueAsString(errorResponse);
    response.getWriter().write(jsonResponse);
  }

  private static class ErrorResponse {
    public final String error;
    public final String message;
    public final int status;
    public final String path;

    public ErrorResponse(String error, String message, int status, String path) {
      this.error = error;
      this.message = message;
      this.status = status;
      this.path = path;
    }
  }
}