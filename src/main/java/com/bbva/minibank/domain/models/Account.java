package com.bbva.minibank.domain.models;

import com.bbva.minibank.domain.models.enums.CurrencyEnum;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  private UUID accountNumber;
  private BigDecimal balance;
  private CurrencyEnum currency;
  private List<Client> holders;
  private List<Transaction> transactions;

}
