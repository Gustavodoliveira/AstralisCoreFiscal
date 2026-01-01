package com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.taxApuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxApurationResponse {

  private UUID id;
  private UUID enterpriseId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private LocalDateTime apuredAt;
  private BigDecimal icmsValue;
  private BigDecimal pisValue;
  private BigDecimal cofinsValue;
  private LocalDateTime createdAt;
}
