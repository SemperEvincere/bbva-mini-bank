package com.bbva.minibank.presentation.controllers;

import com.bbva.minibank.application.usecases.client.IClientCreateUseCase;
import com.bbva.minibank.application.usecases.client.IClientFindByUseCase;
import com.bbva.minibank.application.usecases.client.IClientSaveUseCase;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.mappers.ClientMapper;
import com.bbva.minibank.presentation.response.errors.ErrorResponse;
import com.bbva.minibank.presentation.request.client.ClientCreateRequest;
import com.bbva.minibank.presentation.response.client.ClientResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final IClientFindByUseCase clientFindByUseCase;
  private final ClientMapper clientMapper;

  @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> create(@Valid @RequestBody ClientCreateRequest request, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      List<String> errors = bindingResult.getFieldErrors().stream()
          .map(FieldError::getDefaultMessage)
          .collect(Collectors.toList());

      ErrorResponse errorResponse = new ErrorResponse("Error de validación", errors);
      return ResponseEntity.badRequest().body(errorResponse);
    }

    Client client = clientCreateUseCase.create(request);
    ClientResponse response = clientMapper.toResponse(clientSaveUseCase.save(client));
    return new ResponseEntity<>(response, null, 201);

  }

  @GetMapping(value = "/", produces = "application/json")
  public ResponseEntity<?> getAll() {
    List<ClientResponse> response = clientFindByUseCase
        .getAll()
        .stream()
        .map(clientMapper::toResponse)
        .collect(Collectors.toList());
    return new ResponseEntity<>(response, null, 200);
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<?> getById(@PathVariable("id") UUID id) {
    ClientResponse response = clientMapper.toResponse(clientFindByUseCase.findById(id));

    return new ResponseEntity<>(response, null, 200);
  }
}
