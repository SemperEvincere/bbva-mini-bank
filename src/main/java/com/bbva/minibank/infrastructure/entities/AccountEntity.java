package com.bbva.minibank.infrastructure.entities;

import com.bbva.minibank.domain.models.enums.CurrencyEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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

  @ElementCollection(fetch = FetchType.EAGER)
  private Map<String, UUID> holders;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<TransactionEntity> transactions;

}
