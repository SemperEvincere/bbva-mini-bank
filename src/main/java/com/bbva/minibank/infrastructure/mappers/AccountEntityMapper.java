package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEntityMapper {

  private final TransactionEntityMapper transactionMapper;

  public Account entityToDomain(AccountEntity accountEntity) {
    return Account.builder()
        .accountNumber(accountEntity.getAccountNumber())
        .balance(accountEntity.getBalance())
        .currency(accountEntity.getCurrency())
        .holders(accountEntity.getHolders())
        .transactions(accountEntity.getTransactions().stream()
            .map(transactionMapper::toDomain)
            .toList())
        .build();
  }

  public AccountEntity domainToEntity(Account account) {
    return AccountEntity.builder()
        .accountNumber(account.getAccountNumber())
        .balance(account.getBalance())
        .currency(account.getCurrency())
        .holders(account.getHolders())
        .transactions(account.getTransactions().stream()
            .map(transactionMapper::toEntity)
            .toList())
        .build();
  }
}
