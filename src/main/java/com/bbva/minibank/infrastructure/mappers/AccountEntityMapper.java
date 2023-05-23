package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEntityMapper {

  private final TransactionEntityMapper transactionMapper;

  public Account entityToDomain(AccountEntity accountEntity) {
    if (accountEntity == null) {
      return null;
    }

    return Account.builder().accountNumber(accountEntity.getAccountNumber()).balance(accountEntity.getBalance()).currency(accountEntity.getCurrency()).clientHolder(accountEntity.getHolders().get("holder")).clientSecondHolder(accountEntity.getHolders().getOrDefault("coholder", null))
        .transactions(Optional.ofNullable(accountEntity.getTransactions()).orElse(Collections.emptyList()).stream().map(transactionMapper::toDomain).toList()).build();
  }

  public AccountEntity domainToEntity(Account account) {
    if (account == null) {
      return null;
    }
    AccountEntity.AccountEntityBuilder accountBuilder = AccountEntity.builder().accountNumber(account.getAccountNumber()).balance(account.getBalance()).currency(account.getCurrency());

    Map<String, UUID> holdersMap = new HashMap<>();
    holdersMap.put("holder", account.getClientHolder());
    if (account.getClientSecondHolder() != null) {
      holdersMap.put("coholder", account.getClientSecondHolder());
    }
    accountBuilder.holders(holdersMap);

    List<Transaction> transactionEntities = account.getTransactions();
    if (transactionEntities != null && !transactionEntities.isEmpty()) {
      accountBuilder.transactions(transactionEntities.stream().map(transactionMapper::toEntity).toList());
    }

    return accountBuilder.build();
  }
}
