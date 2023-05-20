package com.bbva.minibank.application.usecases.transaction;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.domain.models.Transaction;

public interface ITransactionBalanceUseCase {
Transaction deposit(Transaction transaction,
    Client clientSaved);


  void withdraw(Transaction transaction);

  void transfer(Transaction transaction,
      Client clientSaved);
}
