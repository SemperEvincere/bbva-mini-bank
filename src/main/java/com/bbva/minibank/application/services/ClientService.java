package com.bbva.minibank.application.services;

import com.bbva.minibank.application.repository.IClientRepository;
import com.bbva.minibank.application.usecases.client.IClientCreateUseCase;
import com.bbva.minibank.application.usecases.client.IClientSaveUseCase;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.mappers.ClientMapper;
import com.bbva.minibank.presentation.request.ClientCreateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientService implements IClientCreateUseCase, IClientSaveUseCase {

  private final ClientMapper clientMapper;
  private final IClientRepository clientRepository;
  @Override
  public Client create(ClientCreateRequest request) {
    return clientMapper.toClient(request);
  }

  @Override
  public Client save(Client client) {
    return clientRepository.saveClient(client);
  }
}
