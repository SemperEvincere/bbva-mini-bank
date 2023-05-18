package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import com.bbva.minibank.infrastructure.entities.TransactionEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityMapper {


  public List<AccountEntity> domainToEntity(Client client) {
    List<Account> accounts = client.getAccounts();
    return accounts.stream()
        .map(account -> AccountEntity.builder()
            .accountNumber(account.getAccountNumber())
            .balance(account.getBalance())
            .currency(account.getCurrency())
            .transactions(new ArrayList<TransactionEntity>())
            .build())
        .toList();
  }
}
