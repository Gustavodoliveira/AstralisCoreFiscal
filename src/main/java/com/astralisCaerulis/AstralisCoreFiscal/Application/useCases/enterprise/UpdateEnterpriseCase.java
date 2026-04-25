package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.enterprise;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.enterprise.EnterpriseNotFoundException;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.Enterprise;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.EnterpriseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateEnterpriseCase {

  private final EnterpriseRepository enterpriseRepository;

  public Enterprise execute(String enterpriseId, Enterprise updates) {
    UUID id = UUID.fromString(enterpriseId);

    enterpriseRepository.findById(id)
        .orElseThrow(() -> new EnterpriseNotFoundException("Enterprise not found with id: " + enterpriseId));

    return enterpriseRepository.update(id, updates);
  }
}
