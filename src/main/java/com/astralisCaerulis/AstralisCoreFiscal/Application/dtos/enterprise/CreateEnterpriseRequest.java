package com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.enterprise;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.LegalNature;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.TaxRegime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEnterpriseRequest {

  @NotNull(message = "Owner user ID is required")
  private UUID ownerUserId;

  @NotBlank(message = "Enterprise name is required")
  private String enterpriseName;

  @NotBlank(message = "Corporate name is required")
  private String corporateName;

  @NotBlank(message = "CNPJ is required")
  private String cnpj;

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;

  private String phone;

  @NotBlank(message = "State registration is required")
  private String stateRegistration;

  @NotNull(message = "Tax regime is required")
  private TaxRegime taxRegime;

  @NotNull(message = "Legal nature is required")
  private LegalNature legalNature;
}
