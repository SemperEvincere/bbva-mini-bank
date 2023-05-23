package com.bbva.minibank.presentation.controllers;

import com.bbva.minibank.application.usecases.account.IAccountCreateUseCase;
import com.bbva.minibank.application.usecases.client.IClientFindByUseCase;
import com.bbva.minibank.application.usecases.client.IClientUpdateUseCase;
import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.presentation.mappers.AccountPresentationMapper;
import com.bbva.minibank.presentation.request.account.AccountCreateRequest;
import com.bbva.minibank.presentation.response.account.AccountCreateResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

  private final IAccountCreateUseCase accountCreateUseCase;
  private final AccountPresentationMapper accountMapper;
  private final IClientFindByUseCase clientFindByUseCase;
  private final IClientUpdateUseCase clientUpdateUseCase;

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

    Account account = accountCreateUseCase.create(accountCreateRequest.getCurrency(), client.get(), secondHolder.orElse(null));
    client.get().getAccounts().add(account.getAccountNumber());
    secondHolder.ifPresent(value -> value.getAccounts().add(account.getAccountNumber()));
    AccountCreateResponse accountCreateResponse = accountMapper.domainToCreateResponse(account);

    return ResponseEntity.ok(accountCreateResponse);
  }
}
