package com.bbva.minibank.infrastructure.entities;

import com.bbva.minibank.domain.models.enums.CurrencyEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
  @Column(nullable = false, unique = true)
  private UUID accountNumber;

  @Column(nullable = false)
  @PositiveOrZero
  private BigDecimal balance;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CurrencyEnum currency;

  @OneToOne(fetch = FetchType.LAZY)
  private ClientEntity owner;

  @ManyToMany(mappedBy = "accounts", fetch = FetchType.LAZY)
  private List<ClientEntity> coHolders;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<TransactionEntity> transactions;

}
