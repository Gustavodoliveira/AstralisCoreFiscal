package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.repositories.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.TaxApurationReport;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.TaxApurationReportRepository;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.EnterpriseEntity;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.mappers.TaxApurationReportMapper;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.repositories.TaxApurationReportJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TaxApurationReportRepositoryImpl implements TaxApurationReportRepository {
  private final TaxApurationReportJpaRepository jpaRepository;
  private final TaxApurationReportMapper mapper;

  @Override
  public TaxApurationReport save(TaxApurationReport report, EnterpriseEntity enterprise) {
    var entity = mapper.toEntity(report, enterprise);

    var savedEntity = jpaRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public Optional<TaxApurationReport> findById(UUID id) {
    return jpaRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public List<TaxApurationReport> findByEnterpriseId(UUID enterpriseId) {
    return jpaRepository.findByEnterprise_Id(enterpriseId).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public void deleteById(UUID id) {
    jpaRepository.deleteById(id);

    return;
  }

}
