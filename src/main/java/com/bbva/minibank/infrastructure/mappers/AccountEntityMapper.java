package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import com.bbva.minibank.infrastructure.entities.TransactionEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        .transactions(Optional.ofNullable(accountEntity.getTransactions())
            .orElse(Collections.emptyList())
            .stream()
            .map(transactionMapper::toDomain)
            .toList())
        .build();
  }

  public AccountEntity domainToEntity(Account account) {
    AccountEntity.AccountEntityBuilder builder = AccountEntity.builder()
        .accountNumber(account.getAccountNumber())
        .balance(account.getBalance())
        .currency(account.getCurrency())
        .holders(account.getHolders());

    List<Transaction> transactionEntities = account.getTransactions();
    if (transactionEntities != null && !transactionEntities.isEmpty()) {
      builder.transactions(transactionEntities.stream()
          .map(transactionMapper::toEntity)
          .toList());
    }

    return builder.build();
  }
}
