package com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.Enterprise;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.UserEntity;

public interface EnterpriseRepository {

  Enterprise save(Enterprise enterprise, UserEntity ownerUser);

  Optional<Enterprise> findById(UUID id);

  Optional<Enterprise> findByCnpj(String cnpj);

  List<Enterprise> findByOwnerUserId(UUID ownerUserId);

  void deleteById(UUID id);
}
