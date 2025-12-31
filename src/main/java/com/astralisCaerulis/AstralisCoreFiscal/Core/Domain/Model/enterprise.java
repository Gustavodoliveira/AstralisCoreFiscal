package com.astralisCaerulis.AstralisCoreFiscal.Core.Domain.Model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.astralisCaerulis.AstralisCoreFiscal.Core.Domain.enums.LegalNature;
import com.astralisCaerulis.AstralisCoreFiscal.Core.Domain.enums.TaxRegime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class enterprise {
  UUID id;

  String companyName;

  String corporateName;

  String cnpj;

  String email;

  String phone;

  String stateRegistration;

  LegalNature legalNature; // Ex: MEI, LTDA, EIRELI

  TaxRegime taxRegime; // Simples Nacional, Lucro Presumido, Lucro Real

  Boolean active;

  UUID userId;

  LocalDateTime createdAt;

  LocalDateTime updatedAt;
}
