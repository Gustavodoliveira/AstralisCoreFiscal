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
    String formattedCnpj = formatCnpj(cnpj);
    return Optional.ofNullable(enterpriseRepository.findByCnpj(formattedCnpj)
        .orElseThrow(
            () -> new com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.enterprise.EnterpriseNotFoundException(
                "Enterprise not found with CNPJ: " + formattedCnpj)));
  }

  /**
   * Formata um CNPJ (somente números) para o padrão XX.XXX.XXX/0001-XX
   */
  private String formatCnpj(String cnpj) {
    String digits = cnpj.replaceAll("\\D", "");
    if (digits.length() != 14) {
      throw new com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.enterprise.EnterpriseNotFoundException(
          "CNPJ deve conter exatamente 14 dígitos");
    }
    return String.format("%s.%s.%s/%s-%s",
        digits.substring(0, 2),
        digits.substring(2, 5),
        digits.substring(5, 8),
        digits.substring(8, 12),
        digits.substring(12, 14));
  }
}
