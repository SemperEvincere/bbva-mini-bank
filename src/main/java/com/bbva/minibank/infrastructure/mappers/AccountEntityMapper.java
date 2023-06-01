package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.infrastructure.entities.AccountEntity;

import java.util.*;

import com.bbva.minibank.infrastructure.entities.ClientEntity;
import com.bbva.minibank.infrastructure.entities.TransactionEntity;
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

      // Construye una instancia de Account utilizando el patrón Builder
      return Account.builder()
              .accountNumber(accountEntity.getAccountNumber())
              .balance(accountEntity.getBalance())
              .currency(accountEntity.getCurrency())
              .clientHolder(accountEntity.getOwner().getId())
              .clientSecondHolder(accountEntity.getCoHolders().isEmpty()? null : accountEntity.getCoHolders().get(0).getId())
              .transactions(Optional.ofNullable(accountEntity.getTransactions()).orElse(Collections.emptyList()).stream().map(transactionMapper::toDomain).toList())
              .build();

  }

  public AccountEntity domainToEntity(Account account) {
    if (account == null) {
      return null;
    }

    AccountEntity.AccountEntityBuilder accountBuilder = AccountEntity.builder()
            .accountNumber(account.getAccountNumber())
            .balance(account.getBalance())
            .currency(account.getCurrency());

    List<ClientEntity> coHolders = new ArrayList<>();

    ClientEntity holderEntity = new ClientEntity();
    holderEntity.setId(account.getClientHolder());
    accountBuilder.owner(holderEntity);

    if (account.getClientSecondHolder() != null) {
      ClientEntity secondHolderEntity = new ClientEntity();
      secondHolderEntity.setId(account.getClientSecondHolder());
      coHolders.add(secondHolderEntity);
    }

    accountBuilder.coHolders(coHolders);
    List<TransactionEntity> transactionEntities = Optional.ofNullable(account.getTransactions())
            .orElse(Collections.emptyList())
            .stream()
            .map(transactionMapper::toEntity)
            .toList();

    accountBuilder.transactions(transactionEntities);

    return accountBuilder.build();
  }
}
