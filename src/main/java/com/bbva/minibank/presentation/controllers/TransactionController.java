package com.bbva.minibank.presentation.controllers;

import com.bbva.minibank.application.usecases.client.IClientFindByUseCase;
import com.bbva.minibank.application.usecases.transaction.ITransactionBalanceUseCase;
import com.bbva.minibank.application.usecases.transaction.ITransactionCreateUseCase;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.domain.models.enums.TransactionTypeEnum;
import com.bbva.minibank.presentation.mappers.TransactionPresentationMapper;
import com.bbva.minibank.presentation.request.transaction.TransactionCreateRequest;
import com.bbva.minibank.presentation.response.transaction.TransactionDepositResponse;
import com.bbva.minibank.presentation.response.errors.ErrorResponse;
import com.bbva.minibank.presentation.response.transaction.TransactionTransferResponse;
import com.bbva.minibank.presentation.response.transaction.TransactionWithdrawalResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final ITransactionCreateUseCase transactionCreateUseCase;
  private final IClientFindByUseCase clientFindByUseCase;
  private final ITransactionBalanceUseCase transactionBalanceUseCase;
  private final TransactionPresentationMapper transactionMapper;

  @PostMapping(value = "/operation", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public ResponseEntity<?> operation(@Valid @RequestBody TransactionCreateRequest transactionCreateRequest,
      BindingResult bindingResult) {
    ResponseEntity<?> errorResponse = getErrorResponseResponseEntity(bindingResult);
    if (errorResponse != null) {
      return errorResponse;
    }

    Client clientSaved = clientFindByUseCase.findById(UUID.fromString(transactionCreateRequest.getIdClient()));
    Transaction transaction = transactionCreateUseCase.createTransaction(transactionCreateRequest);

    switch (TransactionTypeEnum.valueOf(transactionCreateRequest.getType())) {
      case DEPOSIT -> {
        Transaction deposit = transactionBalanceUseCase.deposit(transaction, clientSaved);
        TransactionDepositResponse depositResponse = transactionMapper.toDepositResponse(deposit, clientSaved);
        return ResponseEntity.ok(depositResponse);
      }
      case WITHDRAW -> {
        Transaction withdraw = transactionBalanceUseCase.withdraw(transaction, clientSaved);
        TransactionWithdrawalResponse withdrawalResponse = transactionMapper.toWithdrawalResponse(withdraw, clientSaved);
        return ResponseEntity.ok(withdrawalResponse);
      }
      case TRANSFER -> {
        Transaction transfer = transactionBalanceUseCase.transfer(transaction, clientSaved);
        TransactionTransferResponse transferResponse = transactionMapper.toTransferResponse(transfer, clientSaved);
        return ResponseEntity.ok(transferResponse);
      }
      default -> {
        return ResponseEntity.badRequest().body("Tipo de transacción no válido");
      }
    }
  }


  private static ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(BindingResult bindingResult) {
    if (bindingResult.hasErrors() && bindingResult.hasFieldErrors()) {
      List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

      ErrorResponse errorResponse = new ErrorResponse("Error de validación", errors);
      return ResponseEntity.badRequest().body(errorResponse);
    }
    return null;
  }
}
