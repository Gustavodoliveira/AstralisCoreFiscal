package com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaxApurationReport {
  UUID id;

  UUID enterpriseId;

  LocalDateTime starDate;
  LocalDateTime endDate; // competência (ex: 2025-12)
  LocalDateTime apuredAt; // quando apurou/calculou

  BigDecimal icmsValue;
  BigDecimal pisValue;
  BigDecimal cofinsValue;

  LocalDateTime createdAt;
}
