package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.enterprise;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.Enterprise;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.EnterpriseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindEnterpriseByCnpjUseCase {
  private final EnterpriseRepository enterpriseRepository;

  public Optional<Enterprise> execute(String cnpj) {
    return Optional.ofNullable(enterpriseRepository.findByCnpj(cnpj)
        .orElseThrow(() -> new RuntimeException("Enterprise not found with CNPJ: " + cnpj)));
  }
}
