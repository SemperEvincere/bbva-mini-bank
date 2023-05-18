package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import com.bbva.minibank.infrastructure.entities.TransactionEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityMapper {


  public Account entityToDomain(AccountEntity accountEntity) {
    return Account.builder()
        .accountNumber(accountEntity.getAccountNumber())
        .balance(accountEntity.getBalance())
        .currency(accountEntity.getCurrency())
        .build();
  }

  public AccountEntity domainToEntity(Account account) {
    return AccountEntity.builder()
        .accountNumber(account.getAccountNumber())
        .balance(account.getBalance())
        .currency(account.getCurrency())
        .holders(account.getHolders())
        .transactions(new ArrayList<>())
        .build();
  }
}
