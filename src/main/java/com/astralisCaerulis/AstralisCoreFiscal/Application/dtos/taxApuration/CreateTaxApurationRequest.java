package com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.taxApuration;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaxApurationRequest {

  @NotNull(message = "Enterprise ID is required")
  private UUID enterpriseId;

  @NotNull(message = "Start date is required")
  private LocalDateTime startDate;

  @NotNull(message = "End date is required")
  private LocalDateTime endDate;

  @NotNull(message = "ICMS value is required")
  private BigDecimal icmsValue;

  @NotNull(message = "PIS value is required")
  private BigDecimal pisValue;

  @NotNull(message = "COFINS value is required")
  private BigDecimal cofinsValue;
}
