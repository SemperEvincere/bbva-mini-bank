package com.bbva.minibank.presentation.controllers;

import com.bbva.minibank.application.usecases.client.IClientCreateUseCase;
import com.bbva.minibank.application.usecases.client.IClientSaveUseCase;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.presentation.request.ClientCreateRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

  private final IClientCreateUseCase clientCreateUseCase;
  private final IClientSaveUseCase clientSaveUseCase;

  @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> create(@RequestBody ClientCreateRequest request) {
    Client client = clientCreateUseCase.create(request);
    Client response = clientSaveUseCase.save(client);
    return new ResponseEntity<>(client, null, 201);
  }
}
