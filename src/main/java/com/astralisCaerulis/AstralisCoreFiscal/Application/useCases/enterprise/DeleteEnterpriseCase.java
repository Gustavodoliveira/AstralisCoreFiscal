package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.enterprise;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.enterprise.EnterpriseNotFoundException;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.EnterpriseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteEnterpriseCase {
  private final EnterpriseRepository enterpriseRepository;

  public void execute(String enterpriseId) {
    UUID id = UUID.fromString(enterpriseId);

    enterpriseRepository.findById(id)
        .orElseThrow(() -> new EnterpriseNotFoundException("Enterprise not found with id: " + enterpriseId));

    enterpriseRepository.deleteById(id);
  }
}
