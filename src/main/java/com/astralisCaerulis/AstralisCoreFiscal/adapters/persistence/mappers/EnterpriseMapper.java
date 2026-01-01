package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.mappers;

import org.springframework.stereotype.Component;

import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.enterprise.CreateEnterpriseRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.enterprise.EnterpriseResponse;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.enterprise.UpdateEnterpriseRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.Enterprise;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.EnterpriseEntity;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.UserEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnterpriseMapper {

  public EnterpriseEntity toEntity(Enterprise enterprise, UserEntity ownerUser) {
    if (enterprise == null) {
      return null;
    }

    EnterpriseEntity entity = new EnterpriseEntity();
    entity.setId(enterprise.getId());
    entity.setOwnerUserId(ownerUser);
    entity.setEnterpriseName(enterprise.getEnterpriseName());
    entity.setCorporateName(enterprise.getCorporateName());
    entity.setCnpj(enterprise.getCnpj());
    entity.setEmail(enterprise.getEmail());
    entity.setPhone(enterprise.getPhone());
    entity.setStateRegistration(enterprise.getStateRegistration());
    entity.setTaxRegime(enterprise.getTaxRegime());
    entity.setLegalNature(enterprise.getLegalNature());
    entity.setActive(enterprise.isActive());

    return entity;
  }

  public Enterprise toDomain(EnterpriseEntity entity) {
    if (entity == null) {
      return null;
    }

    return new Enterprise(
        entity.getId(),
        entity.getOwnerUserId() != null ? entity.getOwnerUserId().getId() : null,
        entity.getEnterpriseName(),
        entity.getCorporateName(),
        entity.getCnpj(),
        entity.getEmail(),
        entity.getPhone(),
        entity.getStateRegistration(),
        entity.getTaxRegime(),
        entity.getLegalNature(),
        entity.isActive());
  }

  // DTO -> Domain
  public Enterprise toDomain(CreateEnterpriseRequest request) {
    if (request == null) {
      return null;
    }

    return new Enterprise(
        null,
        request.getOwnerUserId(),
        request.getEnterpriseName(),
        request.getCorporateName(),
        request.getCnpj(),
        request.getEmail(),
        request.getPhone(),
        request.getStateRegistration(),
        request.getTaxRegime(),
        request.getLegalNature(),
        true);
  }

  public Enterprise toDomain(UpdateEnterpriseRequest request) {
    if (request == null) {
      return null;
    }

    return new Enterprise(
        null,
        null,
        request.getEnterpriseName(),
        request.getCorporateName(),
        null,
        request.getEmail(),
        request.getPhone(),
        request.getStateRegistration(),
        request.getTaxRegime(),
        request.getLegalNature(),
        request.getIsActive() != null ? request.getIsActive() : true);
  }

  // Domain -> DTO Response
  public EnterpriseResponse toResponse(Enterprise enterprise) {
    if (enterprise == null) {
      return null;
    }

    return new EnterpriseResponse(
        enterprise.getId(),
        enterprise.getOwnerUserId(),
        enterprise.getEnterpriseName(),
        enterprise.getCorporateName(),
        enterprise.getCnpj(),
        enterprise.getEmail(),
        enterprise.getPhone(),
        enterprise.getStateRegistration(),
        enterprise.getTaxRegime(),
        enterprise.getLegalNature(),
        enterprise.isActive());
  }
}
