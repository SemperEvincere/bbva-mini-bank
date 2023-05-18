package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityMapper {


  public AccountEntity mapToEntity(Account account, ClientEntityMapper clientEntityMapper, TransactionEntityMapper transactionEntityMapper) {
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setAccountNumber(account.getAccountNumber());
    accountEntity.setBalance(account.getBalance());
    accountEntity.setCurrency(account.getCurrency());
    accountEntity.setHolders
        (account.getHolders()
            .stream()
            .map(clientEntityMapper::domainToEntity)
            .collect(Collectors.toList()));
    if(account.getTransactions().isEmpty()) {
      accountEntity.setTransactions(null);
    } else {
      accountEntity.setTransactions
          (account.getTransactions()
              .stream()
              .map(transactionEntityMapper::toEntity)
              .collect(Collectors.toList()));
    }

    return null;
  }

  public Account mapToDomain(AccountEntity accountEntity,
      ClientEntityMapper clientEntityMapper, TransactionEntityMapper transactionEntityMapper) {
    Account account = new Account();
    account.setAccountNumber(accountEntity.getAccountNumber());
    account.setBalance(accountEntity.getBalance());
    account.setCurrency(accountEntity.getCurrency());
    account.setHolders
        (accountEntity.getHolders()
            .stream()
            .map(clientEntityMapper::entityToDomain)
            .collect(Collectors.toList()));
    if(accountEntity.getTransactions() == null) {
      account.setTransactions(null);
    } else {
      account.setTransactions
          (accountEntity.getTransactions()
              .stream()
              .map(transactionEntityMapper::toDomain)
              .collect(Collectors.toList()));
    }

    return account;
  }
}
