package com.bbva.minibank.application.services;

import com.bbva.minibank.application.repository.IClientRepository;
import com.bbva.minibank.application.usecases.account.IAccountCreateUseCase;
import com.bbva.minibank.application.usecases.client.IClientCreateUseCase;
import com.bbva.minibank.application.usecases.client.IClientFindByUseCase;
import com.bbva.minibank.application.usecases.client.IClientSaveUseCase;
import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.domain.models.Transaction;
import com.bbva.minibank.presentation.mappers.ClientPresentationMapper;
import com.bbva.minibank.presentation.request.client.ClientCreateRequest;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientCreateUseCase, IClientSaveUseCase, IClientFindByUseCase {

  private final IClientRepository clientRepository;
  private final IAccountCreateUseCase accountCreateUseCase;
  private final ClientPresentationMapper clientMapper;
  private final AccountService accountService;

  @Override
  public Client create(ClientCreateRequest request) {
    return clientMapper.requestToDomain(request);
  }

  @Override
  public Client save(Client client) {
    List<Account> accountsDefault = accountCreateUseCase.create();
    for (Account account : accountsDefault) {
      client.getAccounts().add(account.getAccountNumber());
      account.getHolders().add(client.getId());
    }
    accountService.saveAll(accountsDefault);
    return clientRepository.saveClient(client);
  }

  @Override
  public List<Client> getAll() {
    return clientRepository.getAll();
  }

  @Override
  public Client findById(UUID id) {
    return clientRepository.findById(id);
  }

  public UUID getAccountClient(Transaction transaction,
      Client client) {
    return client.getAccounts().stream()
        .filter(acc -> acc.equals(transaction.getAccountNumberFrom()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
  }
}
