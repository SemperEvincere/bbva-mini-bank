package com.bbva.minibank.application.repository;

import com.bbva.minibank.domain.models.Transaction;

public interface ITransactionRepository {

  Transaction save(Transaction transaction);
}
