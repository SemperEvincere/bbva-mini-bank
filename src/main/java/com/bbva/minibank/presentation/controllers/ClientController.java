package com.bbva.minibank.presentation.controllers;

import com.bbva.minibank.application.usecases.account.IAccountFindUseCase;
import com.bbva.minibank.application.usecases.client.IClientCreateUseCase;
import com.bbva.minibank.application.usecases.client.IClientFindByUseCase;
import com.bbva.minibank.application.usecases.client.IClientSaveUseCase;
import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.presentation.mappers.AccountPresentationMapper;
import com.bbva.minibank.presentation.mappers.ClientPresentationMapper;
import com.bbva.minibank.presentation.request.client.ClientCreateRequest;
import com.bbva.minibank.presentation.response.account.AccountResponse;
import com.bbva.minibank.presentation.response.client.ClientAllDataResponse;
import com.bbva.minibank.presentation.response.client.ClientResponse;
import com.bbva.minibank.presentation.response.errors.ErrorResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

  private final IClientCreateUseCase clientCreateUseCase;
  private final IClientSaveUseCase clientSaveUseCase;
  private final IClientFindByUseCase clientFindByUseCase;
  private final ClientPresentationMapper clientMapper;
  private final AccountPresentationMapper accountMapper;
  private final IAccountFindUseCase accountFindUseCase;

  private static ResponseEntity<ErrorResponse> getErrorResponseEntity(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

      ErrorResponse errorResponse = new ErrorResponse("Error de validación", errors);
      return ResponseEntity.badRequest().body(errorResponse);
    }
    return null;
  }

  @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> create(@Valid @RequestBody ClientCreateRequest request,
      BindingResult bindingResult) {
    ResponseEntity<ErrorResponse> errorResponse = getErrorResponseEntity(bindingResult);
    if (errorResponse != null) {
      return errorResponse;
    }

    Client client = clientCreateUseCase.create(request);
    Client savedClient = clientSaveUseCase.save(client);
    ClientResponse response = clientMapper.domainToResponse(savedClient);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);

  }

  @GetMapping(value = "/", produces = "application/json")
  @ResponseBody
  public List<ClientResponse> getAll() {
    return clientFindByUseCase.getAll().stream().map(clientMapper::domainToResponse).collect(Collectors.toList());
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  @ResponseBody
  public ClientAllDataResponse getOne(@PathVariable("id") UUID id) {
    Optional<Client> clientOptional = clientFindByUseCase.findById(id);

    return clientOptional.map(client -> {
      List<Account> accounts = client.getAccounts().stream().map(accountFindUseCase::findByAccountNumber).toList();
      List<AccountResponse> accountsResponse = accountMapper.domainToResponseList(accounts);
      return clientMapper.domainToAllDataResponse(client, accountsResponse);
    }).orElse(null);
  }


}
