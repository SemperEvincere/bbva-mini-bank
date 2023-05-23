package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.infrastructure.entities.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionEntityMapper {

  public TransactionEntity toEntity(Transaction transaction) {
    return null;
  }

  public Transaction toDomain(TransactionEntity transactionEntity) {
    if (transactionEntity == null) {
      return null;
    }
    return Transaction.builder().id(transactionEntity.getId()).type(transactionEntity.getType()).amount(transactionEntity.getAmount()).accountNumberFrom(transactionEntity.getAccountNumberFrom()).accountNumberTo(transactionEntity.getAccountNumberTo()).createdAt(transactionEntity.getTimestamp())

        .build();
  }

  public TransactionEntity ToEntity(Transaction transaction) {
    if (transaction == null) {
      return null;
    }
    return TransactionEntity.builder().type(transaction.getType()).amount(transaction.getAmount()).accountNumberFrom(transaction.getAccountNumberFrom()).accountNumberTo(transaction.getAccountNumberTo()).timestamp(transaction.getCreatedAt())

        .build();
  }
}
