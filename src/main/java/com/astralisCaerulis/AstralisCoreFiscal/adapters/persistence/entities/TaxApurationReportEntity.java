package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tax_apuration_reports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxApurationReportEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "enterprise_id", nullable = false)
  private EnterpriseEntity enterprise;

  @Column(nullable = false)
  private LocalDateTime starDate;

  @Column(nullable = false)
  private LocalDateTime endDate; // competência (ex: 2025-12)

  @Column(nullable = false)
  private LocalDateTime apuredAt; // quando apurou/calculou

  @Column(nullable = false)
  private BigDecimal icmsValue;

  @Column(nullable = false)
  private BigDecimal pisValue;

  @Column(nullable = false)
  private BigDecimal cofinsValue;

  @Column(nullable = false)
  private LocalDateTime createdAt;
}
