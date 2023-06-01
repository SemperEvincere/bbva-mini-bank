package com.bbva.minibank.application.services;

import com.bbva.minibank.application.repository.ITransactionRepository;
import com.bbva.minibank.application.usecases.account.IAccountFindUseCase;
import com.bbva.minibank.application.usecases.account.IAccountOperationsUseCase;
import com.bbva.minibank.application.usecases.account.IAccountUpdateUseCase;
import com.bbva.minibank.application.usecases.client.IClientFindByUseCase;
import com.bbva.minibank.application.usecases.transaction.ITransactionBalanceUseCase;
import com.bbva.minibank.application.usecases.transaction.ITransactionCreateUseCase;
import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.domain.models.enums.TransactionTypeEnum;
import com.bbva.minibank.presentation.request.transaction.TransactionCreateRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionBalanceUseCase, ITransactionCreateUseCase {

  private final ITransactionRepository transactionRepository;
  private final IAccountFindUseCase accountFind;
  private final IAccountUpdateUseCase accountUpdate;
  private final IClientFindByUseCase clientFindBy;
  private final IAccountOperationsUseCase accountOperationsUseCase;

  public Transaction createTransaction(TransactionCreateRequest transactionCreateRequest) {
    return Transaction
            .builder()
            .createdAt(LocalDateTime.now())
            .type(TransactionTypeEnum.valueOf(transactionCreateRequest.getType()))
            .accountNumberFrom(transactionCreateRequest.getIdAccountOrigin().isBlank() ? null : UUID.fromString(transactionCreateRequest.getIdAccountOrigin()))
            .accountNumberTo(transactionCreateRequest.getIdAccountDestination().isBlank() ? null : UUID.fromString(transactionCreateRequest.getIdAccountDestination()))
            .amount(transactionCreateRequest.getAmount())
            .build();
  }

  @Override
  public Transaction deposit(Transaction transaction,
      Client client) {
    UUID accountClient = clientFindBy.getAccountClient(transaction, client);
    Account accountSaved = accountFind.findByAccountNumber(accountClient);
    accountSaved.setBalance(accountOperationsUseCase.add(accountSaved.getBalance(), transaction.getAmount()));

    if (accountSaved.getTransactions() == null || accountSaved.getTransactions().isEmpty()) {
      accountSaved.setTransactions(new ArrayList<>());
    }
    Transaction transactionSaved = transactionRepository.save(transaction);
    accountSaved.getTransactions().add(transactionSaved);
    accountUpdate.update(accountSaved);
    return transactionSaved;
  }


  @Override
  public Transaction withdraw(Transaction transaction,
      Client client) {
    UUID accountClient = clientFindBy.getAccountClient(transaction, client);
    Account accountSaved = accountFind.findByAccountNumber(accountClient);
    accountSaved.setBalance(accountOperationsUseCase.substract(accountSaved.getBalance(), transaction.getAmount()));
    if (accountSaved.getTransactions() == null || accountSaved.getTransactions().isEmpty()) {
      accountSaved.setTransactions(new ArrayList<>());
    }
    Transaction transactionSaved = transactionRepository.save(transaction);
    accountSaved.getTransactions().add(transactionSaved);
    accountUpdate.update(accountSaved);
    return transactionSaved;
  }

  @Override
  public Transaction transfer(Transaction transaction,
      Client clientSaved) {
    UUID accountClient = clientFindBy.getAccountClient(transaction, clientSaved);
    Account accountOrigin = accountFind.findByAccountNumber(accountClient);
    Account accountDestination = accountFind.findByAccountNumber(transaction.getAccountNumberTo());
    accountOrigin.setBalance(accountOperationsUseCase.substract(accountOrigin.getBalance(), transaction.getAmount()));
    accountDestination.setBalance(accountOperationsUseCase.add(accountDestination.getBalance(), transaction.getAmount()));
    if (accountOrigin.getTransactions() == null || accountOrigin.getTransactions().isEmpty()) {
      accountOrigin.setTransactions(new ArrayList<>());
    }
    if (accountDestination.getTransactions() == null || accountDestination.getTransactions().isEmpty()) {
      accountDestination.setTransactions(new ArrayList<>());
    }
    Transaction transactionSaved = transactionRepository.save(transaction);
    accountOrigin.getTransactions().add(transactionSaved);
    accountDestination.getTransactions().add(transactionSaved);
    accountUpdate.update(accountOrigin);
    accountUpdate.update(accountDestination);

    return transactionSaved;
  }


}
