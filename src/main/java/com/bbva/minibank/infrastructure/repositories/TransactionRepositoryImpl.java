package com.bbva.minibank.infrastructure.repositories;

import com.bbva.minibank.application.repository.ITransactionRepository;
import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.infrastructure.entities.TransactionEntity;
import com.bbva.minibank.infrastructure.mappers.TransactionEntityMapper;
import com.bbva.minibank.infrastructure.repositories.springdatajpa.ITransactionSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements ITransactionRepository {

  private final ITransactionSpringRepository transactionSpringRepository;
  private final TransactionEntityMapper transactionEntityMapper;

  @Override
  public Transaction save(Transaction transaction) {
    TransactionEntity transactionEntity = transactionSpringRepository.save(transactionEntityMapper.ToEntity(transaction));
    return transactionEntityMapper.toDomain(transactionEntity);
  }
}
