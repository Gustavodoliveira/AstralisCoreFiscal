package com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.enterprise;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.LegalNature;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.TaxRegime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseResponse {

  private UUID id;
  private UUID ownerUserId;
  private String enterpriseName;
  private String corporateName;
  private String cnpj;
  private String email;
  private String phone;
  private String stateRegistration;
  private TaxRegime taxRegime;
  private LegalNature legalNature;
  private boolean isActive;
}
