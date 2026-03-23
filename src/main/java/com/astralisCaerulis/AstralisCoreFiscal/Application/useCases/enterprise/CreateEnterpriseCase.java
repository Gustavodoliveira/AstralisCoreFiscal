package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.enterprise;

import org.springframework.stereotype.Service;

import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.enterprise.CnpjAlreadyExistsException;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.Enterprise;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.EnterpriseRepository;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateEnterpriseCase {
  private final EnterpriseRepository enterpriseRepository;

  public Enterprise execute(Enterprise enterprise, UserEntity ownerUserId) {
    // Verificar se CNPJ já está em uso
    enterpriseRepository.findByCnpj(enterprise.getCnpj())
        .ifPresent(e -> {
          throw new CnpjAlreadyExistsException("CNPJ already exists: " + enterprise.getCnpj());
        });

    return enterpriseRepository.save(enterprise, ownerUserId);
  }
}
