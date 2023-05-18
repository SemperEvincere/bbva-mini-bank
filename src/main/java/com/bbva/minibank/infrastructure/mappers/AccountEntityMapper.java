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
    Account account = new Account();
    account.setAccountNumber(accountEntity.getAccountNumber());
    account.setBalance(accountEntity.getBalance());
    account.setCurrency(accountEntity.getCurrency());
    return account;
  }

  public Account domainToEntity(Account account) {
    Account accountEntity = new Account();
    accountEntity.setAccountNumber(account.getAccountNumber());
    accountEntity.setBalance(account.getBalance());
    accountEntity.setCurrency(account.getCurrency());
    return accountEntity;
  }
}
