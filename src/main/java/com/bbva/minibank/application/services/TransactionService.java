package com.bbva.minibank.application.services;

import com.bbva.minibank.application.repository.ITransactionRepository;
import com.bbva.minibank.application.usecases.account.IAccountFindUseCase;
import com.bbva.minibank.application.usecases.account.IAccountUpdateUseCase;
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
public class TransactionService implements
    ITransactionBalanceUseCase,
    ITransactionCreateUseCase {

  private final ITransactionRepository transactionRepository;
  private final IAccountFindUseCase accountFind;
  private final IAccountUpdateUseCase accountUpdate;
  private final ClientService clientService;
  private final AccountService accountService;

  public Transaction createTransaction(TransactionCreateRequest transactionCreateRequest) {
    return Transaction.builder()
        .createdAt(LocalDateTime.now())
        .type(TransactionTypeEnum.valueOf(transactionCreateRequest.getType()))
        .accountNumberFrom(transactionCreateRequest.getIdAccountDestination().isBlank()? null : UUID.fromString(transactionCreateRequest.getIdAccountDestination()))
        .accountNumberTo(UUID.fromString(transactionCreateRequest.getIdAccountDestination()))
        .amount(transactionCreateRequest.getAmount())
        .build();
  }
  @Override
  public Transaction deposit(Transaction transaction,
      Client client) {
    UUID accountClient = clientService.getAccountClient(transaction, client);
    Account accountSaved = accountFind.findByAccountNumber(accountClient);
    accountSaved.setBalance(accountService.add(accountSaved.getBalance(), transaction.getAmount()));

    if(accountSaved.getTransactions()== null || accountSaved.getTransactions().isEmpty())
      accountSaved.setTransactions(new ArrayList<>());
    Transaction transactionSaved = transactionRepository.save(transaction);
    accountSaved.getTransactions().add(transactionSaved);
    accountUpdate.update(accountSaved);
    return transactionSaved;
  }



  @Override
  public Transaction withdraw(Transaction transaction, Client client) {
    UUID accountClient = clientService.getAccountClient(transaction, client);
    Account accountSaved = accountFind.findByAccountNumber(accountClient);
    accountSaved.setBalance(accountService.substract(accountSaved.getBalance(), transaction.getAmount()));
    if(accountSaved.getTransactions()== null || accountSaved.getTransactions().isEmpty())
      accountSaved.setTransactions(new ArrayList<>());
    Transaction transactionSaved = transactionRepository.save(transaction);
    accountSaved.getTransactions().add(transactionSaved);
    accountUpdate.update(accountSaved);
    return transactionSaved;
  }

  @Override
  public void transfer(Transaction transaction,
      Client clientSaved) {

  }


}
