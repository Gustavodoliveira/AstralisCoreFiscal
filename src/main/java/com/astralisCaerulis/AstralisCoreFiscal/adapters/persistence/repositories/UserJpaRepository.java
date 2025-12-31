package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findByEmail(String email);
}
