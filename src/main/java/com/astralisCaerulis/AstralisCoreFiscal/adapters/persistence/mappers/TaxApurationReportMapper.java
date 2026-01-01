package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.mappers;

import org.springframework.stereotype.Component;

import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.taxApuration.CreateTaxApurationRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.taxApuration.TaxApurationResponse;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.taxApuration.UpdateTaxApurationRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.TaxApurationReport;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.EnterpriseEntity;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.TaxApurationReportEntity;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

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

  // DTO -> Domain
  public TaxApurationReport toDomain(CreateTaxApurationRequest request) {
    if (request == null) {
      return null;
    }

    return new TaxApurationReport(
        null,
        request.getEnterpriseId(),
        request.getStartDate(),
        request.getEndDate(),
        LocalDateTime.now(),
        request.getIcmsValue(),
        request.getPisValue(),
        request.getCofinsValue(),
        LocalDateTime.now());
  }

  public TaxApurationReport toDomain(UpdateTaxApurationRequest request) {
    if (request == null) {
      return null;
    }

    return new TaxApurationReport(
        null,
        null,
        request.getStartDate(),
        request.getEndDate(),
        null,
        request.getIcmsValue(),
        request.getPisValue(),
        request.getCofinsValue(),
        null);
  }

  // Domain -> DTO Response
  public TaxApurationResponse toResponse(TaxApurationReport report) {
    if (report == null) {
      return null;
    }

    return new TaxApurationResponse(
        report.getId(),
        report.getEnterpriseId(),
        report.getStarDate(),
        report.getEndDate(),
        report.getApuredAt(),
        report.getIcmsValue(),
        report.getPisValue(),
        report.getCofinsValue(),
        report.getCreatedAt());
  }
}
