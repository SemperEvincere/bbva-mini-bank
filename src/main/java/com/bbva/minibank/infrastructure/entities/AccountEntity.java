package com.bbva.minibank.infrastructure.entities;

import com.bbva.minibank.domain.models.enums.CurrencyEnum;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {

  @Id
  @Nonnull
  private UUID accountNumber;
  private BigDecimal balance;
  @Enumerated(EnumType.STRING)
  private CurrencyEnum currency;
  @ElementCollection(fetch = FetchType.LAZY)
  private List<UUID> holders;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<TransactionEntity> transactions;

}
