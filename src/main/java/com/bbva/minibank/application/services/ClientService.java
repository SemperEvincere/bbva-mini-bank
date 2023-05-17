package com.bbva.minibank.application.services;

import com.bbva.minibank.application.repository.IClientRepository;
import com.bbva.minibank.application.usecases.client.IClientCreateUseCase;
import com.bbva.minibank.application.usecases.client.IClientFindByUseCase;
import com.bbva.minibank.application.usecases.client.IClientSaveUseCase;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.mappers.ClientMapper;
import com.bbva.minibank.presentation.request.client.ClientCreateRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements IClientCreateUseCase, IClientSaveUseCase, IClientFindByUseCase {

  @Autowired
  private ClientMapper clientMapper;
  @Qualifier("clientRepositoryImpl")
  @Autowired
  private IClientRepository clientRepository;
  @Override
  public Client create(ClientCreateRequest request) {
    return clientMapper.toClient(request);
  }

  @Override
  public Client save(Client client) {
    return clientRepository.saveClient(client);
  }

  @Override
  public List<Client> getAll() {
    return clientRepository.getAll();
  }
}
