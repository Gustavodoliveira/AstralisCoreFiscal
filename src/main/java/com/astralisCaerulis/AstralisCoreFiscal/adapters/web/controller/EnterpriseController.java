package com.astralisCaerulis.AstralisCoreFiscal.adapters.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.enterprise.CreateEnterpriseRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.enterprise.EnterpriseResponse;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.enterprise.CreateEnterpriseCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.enterprise.DeleteEnterpriseCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.enterprise.FindEnterpriseByCnpjUseCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.enterprise.UpdateEnterpriseCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.enterprise.FindEnterpriseByIdUseCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.enterprise.UpdateEnterpriseRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.user.UserNotFoundException;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.mappers.EnterpriseMapper;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.repositories.UserJpaRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/enterprises")
@RequiredArgsConstructor
public class EnterpriseController {

  private static final Logger log = LoggerFactory.getLogger(EnterpriseController.class);

  private final CreateEnterpriseCase createEnterpriseCase;
  private final DeleteEnterpriseCase deleteEnterpriseCase;
  private final FindEnterpriseByCnpjUseCase findEnterpriseByCnpjUseCase;
  private final UpdateEnterpriseCase updateEnterpriseCase;
  private final FindEnterpriseByIdUseCase findEnterpriseByIdUseCase;
  private final EnterpriseMapper enterpriseMapper;
  private final UserJpaRepository userJpaRepository;

  @PostMapping("/create")
  public ResponseEntity<EnterpriseResponse> createEnterprise(@Valid @RequestBody CreateEnterpriseRequest request) {
    var ownerUser = userJpaRepository.findById(request.getOwnerUserId())
        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.getOwnerUserId()));

    var enterprise = enterpriseMapper.toDomain(request);
    var created = createEnterpriseCase.execute(enterprise, ownerUser);

    return ResponseEntity.ok(enterpriseMapper.toResponse(created));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<EnterpriseResponse> updateEnterprise(@PathVariable String id,
      @Valid @RequestBody UpdateEnterpriseRequest request) {
    var enterprise = enterpriseMapper.toDomain(request);
    var updated = updateEnterpriseCase.execute(id, enterprise);
    return ResponseEntity.ok(enterpriseMapper.toResponse(updated));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteEnterprise(@PathVariable String id) {
    deleteEnterpriseCase.execute(id);
    return ResponseEntity.status(204).body("Empresa deletada com sucesso");
  }

  @GetMapping("/by-id/{id}")
  public ResponseEntity<EnterpriseResponse> getEnterpriseById(@PathVariable String id) {
    var enterprise = findEnterpriseByIdUseCase.execute(id)
        .orElseThrow(() -> new RuntimeException("Enterprise not found with id: " + id));
    return ResponseEntity.ok(enterpriseMapper.toResponse(enterprise));
  }

  @GetMapping("/by-cnpj/{cnpj}")
  public ResponseEntity<EnterpriseResponse> getEnterpriseByCnpj(@PathVariable String cnpj) {
    log.info("Recebido CNPJ na requisição: {}", cnpj);
    var enterprise = findEnterpriseByCnpjUseCase.execute(cnpj)
        .orElseThrow(
            () -> new com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.enterprise.EnterpriseNotFoundException(
                "Enterprise not found with CNPJ: " + cnpj));
    return ResponseEntity.ok(enterpriseMapper.toResponse(enterprise));

  }

}
