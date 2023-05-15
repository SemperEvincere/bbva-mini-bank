package com.bbva.minibank.infrastructure.entities;

import com.bbva.minibank.domain.models.enums.CurrencyEnum;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountEntity {

  @Id
  @Nonnull
  private UUID accountNumber;
  private BigDecimal balance;
  @Enumerated(EnumType.STRING)
  private CurrencyEnum currency;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ClientEntity> holders;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<TransactionEntity> transactions;

}
