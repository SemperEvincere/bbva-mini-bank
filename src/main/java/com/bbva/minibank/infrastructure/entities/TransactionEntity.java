package com.bbva.minibank.infrastructure.entities;

import com.bbva.minibank.domain.models.enums.TransactionTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

  @Id
  @GeneratedValue(generator = "UUID")
  UUID id;
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime timestamp;
  @Enumerated(value = EnumType.STRING)
  private TransactionTypeEnum type;
  private BigDecimal amount;
  private UUID accountNumberFrom;
  private UUID accountNumberTo;

}
