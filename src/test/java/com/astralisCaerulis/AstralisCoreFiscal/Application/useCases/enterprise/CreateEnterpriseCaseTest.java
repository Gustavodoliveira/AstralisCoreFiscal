package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.enterprise;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.enterprise.CnpjAlreadyExistsException;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.LegalNature;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.TaxRegime;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.Enterprise;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.EnterpriseRepository;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("CreateEnterpriseCase - Testes Unitários")
class CreateEnterpriseCaseTest {

  @Mock
  private EnterpriseRepository enterpriseRepository;

  @InjectMocks
  private CreateEnterpriseCase createEnterpriseCase;

  private Enterprise validEnterprise;
  private UserEntity validUserEntity;

  @BeforeEach
  void setUp() {
    validUserEntity = new UserEntity(
        UUID.randomUUID(),
        "João Silva",
        "joao@example.com",
        "+5511999999999",
        "hashedPassword");

    validEnterprise = new Enterprise(
        null,
        validUserEntity.getId(),
        "Tech Solutions LTDA",
        "Tech Solutions LTDA",
        "12.345.678/0001-90",
        "empresa@techsolutions.com",
        "+5511987654321",
        "123456789",
        TaxRegime.SIMPLES_NACIONAL,
        LegalNature.LTDA,
        true);
  }

  @Test
  @DisplayName("Deve criar empresa com sucesso")
  void shouldCreateEnterpriseSuccessfully() {
    // Arrange
    log.info("🧪 Iniciando teste: Criar empresa com sucesso");

    Enterprise savedEnterprise = new Enterprise(
        UUID.randomUUID(),
        validEnterprise.getOwnerUserId(),
        validEnterprise.getEnterpriseName(),
        validEnterprise.getCorporateName(),
        validEnterprise.getCnpj(),
        validEnterprise.getEmail(),
        validEnterprise.getPhone(),
        validEnterprise.getStateRegistration(),
        validEnterprise.getTaxRegime(),
        validEnterprise.getLegalNature(),
        validEnterprise.isActive());

    when(enterpriseRepository.findByCnpj(validEnterprise.getCnpj())).thenReturn(Optional.empty());
    when(enterpriseRepository.save(any(Enterprise.class), any(UserEntity.class))).thenReturn(savedEnterprise);

    // Act
    Enterprise result = createEnterpriseCase.execute(validEnterprise, validUserEntity);

    // Assert
    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals(validEnterprise.getEnterpriseName(), result.getEnterpriseName());
    assertEquals(validEnterprise.getCorporateName(), result.getCorporateName());
    assertEquals(validEnterprise.getCnpj(), result.getCnpj());
    assertEquals(validEnterprise.getEmail(), result.getEmail());
    assertEquals(validEnterprise.getPhone(), result.getPhone());
    assertEquals(validEnterprise.getStateRegistration(), result.getStateRegistration());
    assertEquals(validEnterprise.getTaxRegime(), result.getTaxRegime());
    assertEquals(validEnterprise.getLegalNature(), result.getLegalNature());
    assertTrue(result.isActive());

    verify(enterpriseRepository, times(1)).findByCnpj(validEnterprise.getCnpj());
    verify(enterpriseRepository, times(1)).save(any(Enterprise.class), any(UserEntity.class));

    log.info("✅ Teste concluído com sucesso: Empresa criada - ID: {}", result.getId());
  }

  @Test
  @DisplayName("Deve lançar exceção quando CNPJ já existe")
  void shouldThrowExceptionWhenCnpjAlreadyExists() {
    // Arrange
    log.info("🧪 Iniciando teste: CNPJ já existe");

    Enterprise existingEnterprise = new Enterprise(
        UUID.randomUUID(),
        UUID.randomUUID(),
        "Empresa Existente LTDA",
        "Empresa Existente LTDA",
        validEnterprise.getCnpj(), // Mesmo CNPJ
        "outra@empresa.com",
        "+5511888888888",
        "987654321",
        TaxRegime.LUCRO_PRESUMIDO,
        LegalNature.LTDA,
        true);

    when(enterpriseRepository.findByCnpj(validEnterprise.getCnpj())).thenReturn(Optional.of(existingEnterprise));

    // Act & Assert
    CnpjAlreadyExistsException exception = assertThrows(
        CnpjAlreadyExistsException.class,
        () -> createEnterpriseCase.execute(validEnterprise, validUserEntity));

    assertEquals("CNPJ already exists: " + validEnterprise.getCnpj(), exception.getMessage());

    verify(enterpriseRepository, times(1)).findByCnpj(validEnterprise.getCnpj());
    verify(enterpriseRepository, never()).save(any(Enterprise.class), any(UserEntity.class));

    log.info("✅ Teste concluído com sucesso: Exceção lançada corretamente para CNPJ duplicado");
  }

  @Test
  @DisplayName("Deve validar CNPJ antes de salvar")
  void shouldValidateCnpjBeforeSaving() {
    // Arrange
    log.info("🧪 Iniciando teste: Validação de CNPJ");

    String cnpjToTest = "98.765.432/0001-10";
    validEnterprise.setCnpj(cnpjToTest);

    when(enterpriseRepository.findByCnpj(cnpjToTest)).thenReturn(Optional.empty());
    when(enterpriseRepository.save(any(Enterprise.class), any(UserEntity.class))).thenReturn(validEnterprise);

    // Act
    createEnterpriseCase.execute(validEnterprise, validUserEntity);

    // Assert
    verify(enterpriseRepository, times(1)).findByCnpj(cnpjToTest);
    verify(enterpriseRepository, times(1)).save(validEnterprise, validUserEntity);

    log.info("✅ Teste concluído com sucesso: CNPJ validado antes de salvar");
  }

  @Test
  @DisplayName("Deve manter dados da empresa após criação")
  void shouldMaintainEnterpriseDataAfterCreation() {
    // Arrange
    log.info("🧪 Iniciando teste: Manter dados da empresa");

    Enterprise savedEnterprise = new Enterprise(
        UUID.randomUUID(),
        validEnterprise.getOwnerUserId(),
        validEnterprise.getEnterpriseName(),
        validEnterprise.getCorporateName(),
        validEnterprise.getCnpj(),
        validEnterprise.getEmail(),
        validEnterprise.getPhone(),
        validEnterprise.getStateRegistration(),
        validEnterprise.getTaxRegime(),
        validEnterprise.getLegalNature(),
        validEnterprise.isActive());

    when(enterpriseRepository.findByCnpj(validEnterprise.getCnpj())).thenReturn(Optional.empty());
    when(enterpriseRepository.save(any(Enterprise.class), any(UserEntity.class))).thenReturn(savedEnterprise);

    // Act
    Enterprise result = createEnterpriseCase.execute(validEnterprise, validUserEntity);

    // Assert
    assertEquals(validEnterprise.getEnterpriseName(), result.getEnterpriseName());
    assertEquals(validEnterprise.getCorporateName(), result.getCorporateName());
    assertEquals(validEnterprise.getCnpj(), result.getCnpj());
    assertEquals(validEnterprise.getEmail(), result.getEmail());
    assertEquals(validEnterprise.getStateRegistration(), result.getStateRegistration());
    assertEquals(validEnterprise.getTaxRegime(), result.getTaxRegime());
    assertEquals(validEnterprise.getLegalNature(), result.getLegalNature());

    log.info("✅ Teste concluído com sucesso: Dados da empresa mantidos corretamente");
  }
}