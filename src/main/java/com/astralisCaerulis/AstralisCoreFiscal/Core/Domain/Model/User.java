package com.astralisCaerulis.AstralisCoreFiscal.Core.Domain.Model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

  UUID id;

  String name;

  String email;

  String password;

  LocalDateTime createdAt;

  LocalDateTime updatedAt;

}
