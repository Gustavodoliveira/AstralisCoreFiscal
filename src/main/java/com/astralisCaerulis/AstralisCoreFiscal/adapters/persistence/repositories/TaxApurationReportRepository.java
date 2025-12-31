package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.TaxApurationReportEntity;

public interface TaxApurationReportRepository extends JpaRepository<TaxApurationReportEntity, UUID> {
  List<TaxApurationReportEntity> findByEnterprise_Id(UUID enterpriseId);
}
