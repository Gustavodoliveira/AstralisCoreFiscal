package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.repositories.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.Enterprise;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.EnterpriseRepository;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.UserEntity;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.mappers.EnterpriseMapper;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.repositories.EnterpriseJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class EnterpriseRepositoryImpl implements EnterpriseRepository {
  private final EnterpriseMapper mapper;
  private final EnterpriseJpaRepository jpaRepository;

  @Override
  public Enterprise save(Enterprise enterprise, UserEntity ownerUser) {
    var entity = mapper.toEntity(enterprise, ownerUser);
    var savedEntity = jpaRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Enterprise> findById(UUID id) {
    return jpaRepository.findById(id)
        .map(mapper::toDomain);
  }

  @Override
  public List<Enterprise> findByOwnerUserId(UUID ownerUserId) {
    return jpaRepository.findByOwnerUserId_Id(ownerUserId).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public void deleteById(UUID id) {
    jpaRepository.deleteById(id);
    return;
  }

}
