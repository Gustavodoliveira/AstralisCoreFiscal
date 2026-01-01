package com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.taxApuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaxApurationRequest {

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private BigDecimal icmsValue;

  private BigDecimal pisValue;

  private BigDecimal cofinsValue;
}
