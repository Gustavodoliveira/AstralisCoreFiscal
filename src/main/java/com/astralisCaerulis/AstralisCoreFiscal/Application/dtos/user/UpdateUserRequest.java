package com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

  @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
  private String name;

  @Email(message = "Invalid email format")
  private String email;

  @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
  private String phone;

  @Size(min = 8, message = "Password must be at least 8 characters")
  private String password;
}
