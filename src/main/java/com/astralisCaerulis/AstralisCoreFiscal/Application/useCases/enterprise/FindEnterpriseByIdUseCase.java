package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.enterprise;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.Enterprise;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.EnterpriseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindEnterpriseByIdUseCase {

  private final EnterpriseRepository enterpriseRepository;

  public Optional<Enterprise> execute(String id) {
    UUID uuid = UUID.fromString(id);
    return Optional.ofNullable(enterpriseRepository.findById(uuid)
        .orElseThrow(() -> new RuntimeException("Enterprise not found with id: " + id)));
  }
}
