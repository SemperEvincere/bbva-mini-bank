package com.bbva.minibank.presentation.mappers;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.presentation.response.transaction.TransactionDepositResponse;
import com.bbva.minibank.presentation.response.transaction.TransactionWithdrawalResponse;
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

  public TransactionWithdrawalResponse toWithdrawalResponse(Transaction withdraw,
      Client clientSaved) {
    return TransactionWithdrawalResponse.builder()
        .id(withdraw.getId().toString())
        .type(withdraw.getType().toString())
        .amountExtracted(withdraw.getAmount().toString())
        .accountNumberFrom(withdraw.getAccountNumberFrom().toString())
        .clientFullName(String.format("%s, %s", clientSaved.getLastName(), clientSaved.getFirstName()))
        .createdAt(withdraw.getCreatedAt().toString())
        .build();
  }
}
