package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.mappers;

import org.springframework.stereotype.Component;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.TaxApurationReport;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.EnterpriseEntity;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.TaxApurationReportEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaxApurationReportMapper {

  public TaxApurationReportEntity toEntity(TaxApurationReport report, EnterpriseEntity enterprise) {
    if (report == null) {
      return null;
    }

    TaxApurationReportEntity entity = new TaxApurationReportEntity();
    entity.setId(report.getId());
    entity.setEnterprise(enterprise);
    entity.setStarDate(report.getStarDate());
    entity.setEndDate(report.getEndDate());
    entity.setApuredAt(report.getApuredAt());
    entity.setIcmsValue(report.getIcmsValue());
    entity.setPisValue(report.getPisValue());
    entity.setCofinsValue(report.getCofinsValue());
    entity.setCreatedAt(report.getCreatedAt());

    return entity;
  }

  public TaxApurationReport toDomain(TaxApurationReportEntity entity) {
    if (entity == null) {
      return null;
    }

    return new TaxApurationReport(
        entity.getId(),
        entity.getEnterprise() != null ? entity.getEnterprise().getId() : null,
        entity.getStarDate(),
        entity.getEndDate(),
        entity.getApuredAt(),
        entity.getIcmsValue(),
        entity.getPisValue(),
        entity.getCofinsValue(),
        entity.getCreatedAt());
  }
}
