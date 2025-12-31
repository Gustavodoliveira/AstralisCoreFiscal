package com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
  UUID id;

  String name;

  String email;

  String phone;

  String passwordHash;
}
