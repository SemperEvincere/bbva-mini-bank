package com.bbva.minibank.application.services;

import com.bbva.minibank.application.repository.IClientRepository;
import com.bbva.minibank.application.usecases.client.IClientCreateUseCase;
import com.bbva.minibank.application.usecases.client.IClientFindByUseCase;
import com.bbva.minibank.application.usecases.client.IClientSaveUseCase;
import com.bbva.minibank.application.usecases.client.IClientUpdateUseCase;
import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.presentation.mappers.ClientPresentationMapper;
import com.bbva.minibank.presentation.request.client.ClientCreateRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientUpdateUseCase, IClientCreateUseCase, IClientSaveUseCase, IClientFindByUseCase {

  private final IClientRepository clientRepository;
  private final ClientPresentationMapper clientMapper;

  @Override
  public Client create(ClientCreateRequest request) {
    String email = request.getEmail();
    if (clientRepository.existsByEmailAndLastNameAndFirstName(email, request.getLastName(), request.getFirstName())) {
      throw new RuntimeException("This client already exists");
    }

    return clientMapper.requestToDomain(request);
  }


  @Override
  public Client save(Client client) {
    return clientRepository.saveClient(client);
  }

  @Override
  public List<Client> getAll() {
    return clientRepository.getAll();
  }

  @Override
  public Optional<Client> findById(UUID id) {
    return clientRepository.findById(id);
  }

  public UUID getAccountClient(Transaction transaction,
      Client client) {
    return client.getAccounts().stream().filter(acc -> acc.equals(transaction.getAccountNumberFrom())).findFirst().orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
  }

  @Override
  public Client update(Client client) {
    return clientRepository.update(client);
  }

  @Override
  public void addAccount(Client client, Account account) {
    clientRepository.addAccount(client, account);
  }
}
