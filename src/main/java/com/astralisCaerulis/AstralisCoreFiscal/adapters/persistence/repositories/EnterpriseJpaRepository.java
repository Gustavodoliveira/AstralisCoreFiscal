package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.EnterpriseEntity;

public interface EnterpriseJpaRepository extends JpaRepository<EnterpriseEntity, UUID> {
  List<EnterpriseEntity> findByOwnerUserId_Id(UUID ownerUserId);
}
