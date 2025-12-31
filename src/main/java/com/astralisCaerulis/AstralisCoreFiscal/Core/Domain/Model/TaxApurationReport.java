package com.astralisCaerulis.AstralisCoreFiscal.Core.Domain.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TaxApurationReport {
  UUID id;

  UUID enterpriseId;

  UUID userId;

  LocalDateTime startedDate;
  LocalDateTime endDate;
  LocalDateTime apuredAt;

  BigDecimal icmsValue;
  BigDecimal pisValue;
  BigDecimal cofinsValue;

  LocalDateTime createdAt;
}
