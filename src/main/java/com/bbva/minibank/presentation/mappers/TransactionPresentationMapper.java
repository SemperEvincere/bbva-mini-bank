package com.bbva.minibank.presentation.mappers;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.presentation.response.TransactionDepositResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionPresentationMapper {

  public TransactionDepositResponse toDepositResponse(Transaction transaction,
      Client clientSaved) {
    return TransactionDepositResponse.builder()
        .id(transaction.getId().toString())
        .type(transaction.getType().toString())
        .amount(transaction.getAmount().toString())
        .accountNumberTo(transaction.getAccountNumberTo().toString())
        .clientFullName(String.format("%s, %s", clientSaved.getLastName(), clientSaved.getFirstName()))
        .createdAt(transaction.getCreatedAt().toString())

        .build();

  }
}
