package com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models;

import java.util.UUID;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.LegalNature;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.TaxRegime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Enterprise {
  UUID id;

  UUID ownerUserId;

  String enterpriseName;

  String corporateName;

  String cnpj;

  String email;

  String phone;

  String stateRegistration;

  TaxRegime taxRegime;

  LegalNature legalNature;

  boolean isActive;
}
