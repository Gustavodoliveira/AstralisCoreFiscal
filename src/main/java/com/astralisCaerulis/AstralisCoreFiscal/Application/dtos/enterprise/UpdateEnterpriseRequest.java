package com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.enterprise;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.LegalNature;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.TaxRegime;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEnterpriseRequest {

  private String enterpriseName;

  private String corporateName;

  @Email(message = "Invalid email format")
  private String email;

  private String phone;

  private String stateRegistration;

  private TaxRegime taxRegime;

  private LegalNature legalNature;

  private Boolean isActive;
}
