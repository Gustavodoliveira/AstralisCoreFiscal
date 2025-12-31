package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities;

import java.util.UUID;

import org.hibernate.validator.constraints.br.CNPJ;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.LegalNature;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.enums.TaxRegime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enterprises")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EnterpriseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_user_id", nullable = false)
  private UserEntity ownerUserId;

  @Column(nullable = false)
  String enterpriseName;

  @Column(nullable = false)
  String corporateName;

  @Column(nullable = false, unique = true)
  @CNPJ
  String cnpj;

  @Column(nullable = false, unique = true)
  @Email
  String email;

  String phone;

  @Column(nullable = false, unique = true)
  String stateRegistration;

  @Column(nullable = false)
  TaxRegime taxRegime;

  @Column(nullable = false)
  LegalNature legalNature;

  @Column(nullable = false)
  boolean isActive;
}
