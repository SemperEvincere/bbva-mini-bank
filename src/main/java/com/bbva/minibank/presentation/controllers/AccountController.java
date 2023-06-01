package com.bbva.minibank.presentation.controllers;

import com.bbva.minibank.application.usecases.account.IAccountCreateUseCase;
import com.bbva.minibank.application.usecases.account.IAccountFindUseCase;
import com.bbva.minibank.application.usecases.account.IAccountUpdateUseCase;
import com.bbva.minibank.application.usecases.client.IClientFindByUseCase;
import com.bbva.minibank.application.usecases.client.IClientUpdateUseCase;
import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.presentation.mappers.AccountPresentationMapper;
import com.bbva.minibank.presentation.request.account.AccountAddCoholder;
import com.bbva.minibank.presentation.request.account.AccountCreateRequest;
import com.bbva.minibank.presentation.response.account.AccountCreateResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

  private final IAccountCreateUseCase accountCreateUseCase;
  private final AccountPresentationMapper accountMapper;
  private final IClientFindByUseCase clientFindByUseCase;
  private final IClientUpdateUseCase clientUpdateUseCase;
  private final IAccountFindUseCase accountFindByUseCase;
  private final IAccountUpdateUseCase accountUpdateUseCase;

  @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> create(@RequestBody AccountCreateRequest accountCreateRequest) {
    if (accountCreateRequest == null) {
      return ResponseEntity.badRequest().body("Request is null");
    }
    Optional<Client> client = clientFindByUseCase.findById(accountCreateRequest.getHolderId());
    if (client.isEmpty()) {
      return ResponseEntity.badRequest().body("Client not found");
    }
    Optional<Client> secondHolder = Optional.empty();
    if (accountCreateRequest.getSecondHolderId() != null) {
      secondHolder = clientFindByUseCase.findById(accountCreateRequest.getSecondHolderId());
      if (secondHolder.isEmpty()) {
        return ResponseEntity.badRequest().body("Second holder not found");
      }
    }
    Optional<Account> accountOptional = client.get()
            .getAccounts()
            .stream()
            .map(accountFindByUseCase::findByAccountNumber)
            .filter(account -> account.getCurrency().equals(accountCreateRequest.getCurrency()))
            .findFirst();

    if (accountOptional.isPresent()) {
      // todo: debe poder crear una cuenta aunque sea cotitular de otra cuenta
      return ResponseEntity.badRequest().body("Account already exists");
    }

    Account account = accountCreateUseCase.create(accountCreateRequest.getCurrency(), client.get(), secondHolder.orElse(null));
    client.get().getAccounts().add(account.getAccountNumber());
    secondHolder.ifPresent(value -> value.getAccounts().add(account.getAccountNumber()));
    AccountCreateResponse accountCreateResponse = accountMapper.domainToCreateResponse(account);
    return ResponseEntity.ok(accountCreateResponse);
  }

  @PostMapping(value = "/coholder", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> coholder(@RequestBody AccountAddCoholder accountAddCoholder) {
    if (accountAddCoholder == null) {
      return ResponseEntity.badRequest().body("Request is null");
    }
    Optional<Client> client = clientFindByUseCase.findById(accountAddCoholder.getHolderId());
    if (client.isEmpty()) {
      return ResponseEntity.badRequest().body("Client not found");
    }
    Optional<Client> secondHolder = Optional.empty();
    if (accountAddCoholder.getSecondHolderId() != null) {
      secondHolder = clientFindByUseCase.findById(accountAddCoholder.getSecondHolderId());
      if (secondHolder.isEmpty()) {
        return ResponseEntity.badRequest().body("Second holder not found");
      }
    }
    Optional<Account> accountOptional = client.get()
            .getAccounts()
            .stream()
            .map(accountFindByUseCase::findByAccountNumber)
            .filter(account -> account.getCurrency().equals(accountAddCoholder.getCurrency()))
            .findFirst();

    if (accountOptional.isEmpty()) {
      return ResponseEntity.badRequest().body("Account not found");
    }

    Account account = accountOptional.get();

    secondHolder.ifPresent(value -> account.setClientSecondHolder(value.getId()));
    secondHolder.ifPresent(value -> value.getAccounts().add(account.getAccountNumber()));
    Account accountUpdate = accountUpdateUseCase.update(account);
    clientUpdateUseCase.update(secondHolder.orElse(null));
    AccountCreateResponse accountCreateResponse = accountMapper.domainToCreateResponse(accountUpdate);
    return ResponseEntity.ok(accountCreateResponse);
  }

  @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> findAll() {
        List<Account> accounts = accountFindByUseCase.findAll();
        List<AccountCreateResponse> accountCreateResponses = accounts.stream()
                .map(accountMapper::domainToCreateResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accountCreateResponses);
    }

    @GetMapping(value = "/{accountNumber}", produces = "application/json")
    public ResponseEntity<?> findByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        Account account = accountFindByUseCase.findByAccountNumber(UUID.fromString(accountNumber));
        AccountCreateResponse accountCreateResponse = accountMapper.domainToCreateResponse(account);
        return ResponseEntity.ok(accountCreateResponse);
    }

}
