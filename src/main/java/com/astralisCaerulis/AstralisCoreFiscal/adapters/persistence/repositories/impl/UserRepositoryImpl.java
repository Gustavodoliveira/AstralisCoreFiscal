package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.UserRepository;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.mappers.UserMapper;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.repositories.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private final UserJpaRepository jpaRepository;
  private final UserMapper mapper;

  @Override
  public User save(User user) {
    var entity = mapper.toEntity(user);
    var savedEntity = jpaRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return jpaRepository.findByEmail(email)
        .map(mapper::toDomain);
  }

  @Override
  public Optional<User> findById(UUID id) {
    return jpaRepository.findById(id)
        .map(mapper::toDomain);
  }

  @Override
  public void deleteById(UUID id) {

    jpaRepository.deleteById(id);

    return;
  }

}
