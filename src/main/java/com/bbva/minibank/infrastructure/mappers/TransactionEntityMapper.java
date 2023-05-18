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
    return null;
  }
}
