package com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

  private UUID id;
  private String name;
  private String email;
  private String phone;
  private String token;
  private String message;
}