package com.bbva.minibank.presentation.controllers;

import com.bbva.minibank.application.usecases.client.IClientCreateUseCase;
import com.bbva.minibank.application.usecases.client.IClientFindByUseCase;
import com.bbva.minibank.application.usecases.client.IClientSaveUseCase;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.presentation.mappers.ClientPresentationMapper;
import com.bbva.minibank.presentation.response.errors.ErrorResponse;
import com.bbva.minibank.presentation.request.client.ClientCreateRequest;
import com.bbva.minibank.presentation.response.client.ClientResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

  private final IClientCreateUseCase clientCreateUseCase;
  private final IClientSaveUseCase clientSaveUseCase;
  private final IClientFindByUseCase clientFindByUseCase;
  private final ClientPresentationMapper clientMapper;

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
    ClientResponse response = clientMapper.domainToResponse(clientSaveUseCase.save(client));
    return new ResponseEntity<>(response, null, 201);

  }

  @GetMapping(value = "/", produces = "application/json")
  public ResponseEntity<List<ClientResponse>> getAll() {
    List<ClientResponse> response = clientFindByUseCase
        .getAll()
        .stream()
        .map(clientMapper::domainToResponse)
        .collect(Collectors.toList());
    return new ResponseEntity<>(response, null, 200);
  }
}
