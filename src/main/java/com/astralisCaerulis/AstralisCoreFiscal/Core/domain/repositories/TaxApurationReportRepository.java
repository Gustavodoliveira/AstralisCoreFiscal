package com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.TaxApurationReport;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.EnterpriseEntity;

public interface TaxApurationReportRepository {
  TaxApurationReport save(TaxApurationReport report, EnterpriseEntity enterprise);

  Optional<TaxApurationReport> findById(UUID id);

  List<TaxApurationReport> findByEnterpriseId(UUID enterpriseId);

  void deleteById(UUID id);
}
