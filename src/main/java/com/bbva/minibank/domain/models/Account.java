package com.bbva.minibank.domain.models;

import com.bbva.minibank.domain.models.enums.CurrencyEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
@AllArgsConstructor
public class Account {

  @NotNull
  private UUID accountNumber;
  @PositiveOrZero
  private BigDecimal balance;
  @Enumerated(EnumType.STRING)
  @NotNull
  private CurrencyEnum currency;
  private List<Client> holders;
  private List<Transaction> transactions;

  public Account() {
    setAccountNumber(UUID.randomUUID());
  }
}
