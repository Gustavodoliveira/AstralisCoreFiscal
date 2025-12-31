package com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;

public interface UserRepository {
  User save(User user);

  Optional<User> findByEmail(String email);

  Optional<User> findById(UUID id);

  void deleteById(UUID id);
}
