package com.bbva.minibank.presentation.controllers;

import com.bbva.minibank.application.usecases.account.IAccountFindUseCase;
import com.bbva.minibank.application.usecases.client.IClientFindByUseCase;
import com.bbva.minibank.application.usecases.transaction.ITransactionBalanceUseCase;
import com.bbva.minibank.application.usecases.transaction.ITransactionCreateUseCase;
import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.domain.models.enums.TransactionTypeEnum;
import com.bbva.minibank.presentation.mappers.TransactionPresentationMapper;
import com.bbva.minibank.presentation.request.transaction.TransactionCreateRequest;
import com.bbva.minibank.presentation.response.errors.ErrorResponse;
import com.bbva.minibank.presentation.response.transaction.TransactionDepositResponse;
import com.bbva.minibank.presentation.response.transaction.TransactionTransferResponse;
import com.bbva.minibank.presentation.response.transaction.TransactionWithdrawalResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final ITransactionCreateUseCase transactionCreateUseCase;
  private final IClientFindByUseCase clientFindByUseCase;
  private final ITransactionBalanceUseCase transactionBalanceUseCase;
  private final TransactionPresentationMapper transactionMapper;
  private final IAccountFindUseCase accountFindUseCase;

  private static ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(BindingResult bindingResult) {
    if (bindingResult.hasErrors() && bindingResult.hasFieldErrors()) {
      List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

      ErrorResponse errorResponse = new ErrorResponse("Error de validación", errors);
      return ResponseEntity.badRequest().body(errorResponse);
    }
    return null;
  }

  @PostMapping(value = "/operation", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public ResponseEntity<?> operation(@Valid @RequestBody TransactionCreateRequest transactionCreateRequest,
      BindingResult bindingResult) {
    ResponseEntity<?> errorResponse = getErrorResponseResponseEntity(bindingResult);
    if (errorResponse != null) {
      return errorResponse;
    }

    Optional<Client> clientSaved = clientFindByUseCase.findById(UUID.fromString(transactionCreateRequest.getIdClient()));
    Transaction transaction = transactionCreateUseCase.createTransaction(transactionCreateRequest);

    if (clientSaved.isEmpty()) {
      return ResponseEntity.badRequest().body("Cliente no encontrado");
    }
    switch (TransactionTypeEnum.valueOf(transactionCreateRequest.getType())) {
      case DEPOSIT -> {
        Transaction deposit = transactionBalanceUseCase.deposit(transaction, clientSaved.get());
        TransactionDepositResponse depositResponse = transactionMapper.toDepositResponse(deposit, clientSaved.get());
        return ResponseEntity.ok(depositResponse);
      }
      case WITHDRAW -> {
        Transaction withdraw = transactionBalanceUseCase.withdraw(transaction, clientSaved.get());
        TransactionWithdrawalResponse withdrawalResponse = transactionMapper.toWithdrawalResponse(withdraw, clientSaved.get());
        return ResponseEntity.ok(withdrawalResponse);
      }
      case TRANSFER -> {
        Transaction transfer = transactionBalanceUseCase.transfer(transaction, clientSaved.get());
        TransactionTransferResponse transferResponse = transactionMapper.toTransferResponse(transfer, clientSaved.get());
        return ResponseEntity.ok(transferResponse);
      }
      default -> {
        return ResponseEntity.badRequest().body("Tipo de transacción no válido");
      }
    }
  }

  @GetMapping(value ="/{accountNumber}", produces = "application/json")
    @ResponseBody
  public ResponseEntity<?> getAllTransactions(@PathVariable String accountNumber) {
    Optional<Account> account = Optional.ofNullable(accountFindUseCase.findByAccountNumber(UUID.fromString(accountNumber)));
    if (account.isEmpty()) {
      return ResponseEntity.badRequest().body("Cuenta no encontrada");
    }
    return ResponseEntity.ok(account.get().getTransactions());
  }
}
