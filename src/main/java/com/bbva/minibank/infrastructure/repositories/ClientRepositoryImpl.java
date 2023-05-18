package com.bbva.minibank.infrastructure.repositories;

import com.bbva.minibank.application.repository.IClientRepository;
import com.bbva.minibank.application.services.AccountService;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import com.bbva.minibank.infrastructure.entities.ClientEntity;
import com.bbva.minibank.infrastructure.mappers.AccountEntityMapper;
import com.bbva.minibank.infrastructure.mappers.ClientEntityMapper;
import com.bbva.minibank.infrastructure.mappers.TransactionEntityMapper;
import com.bbva.minibank.infrastructure.repositories.springdatajpa.IClientSpringRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositoryImpl implements IClientRepository  {

  private final IClientSpringRepository clientSpringRepository;
  private final ClientEntityMapper clientEntityMapper;
  private final AccountEntityMapper accountEntityMapper;
  private final TransactionEntityMapper transactionEntityMapper;
  @Autowired
  private AccountService accountService;

  @Autowired
  public ClientRepositoryImpl(IClientSpringRepository clientSpringRepository, ClientEntityMapper clientEntityMapper, AccountEntityMapper accountEntityMapper,
      TransactionEntityMapper transactionEntityMapper) {
    this.clientSpringRepository = clientSpringRepository;
    this.clientEntityMapper = clientEntityMapper;
    this.accountEntityMapper = accountEntityMapper;
    this.transactionEntityMapper = transactionEntityMapper;
  }
  @Override
  public Client saveClient(Client client) {
    ClientEntity clientEntity = clientEntityMapper.domainToEntity(client);
    List<AccountEntity> accountEntities = client.getAccounts()
        .stream()
        .map(
            accountNumber -> accountEntityMapper.domainToEntity(accountService.findByAccountNumber(accountNumber))
        )
        .collect(Collectors.toList());
    clientEntity.setAccounts(accountEntities);
    clientSpringRepository.save(clientEntity);

    return clientEntityMapper.entityToDomain(clientEntity);
  }

  @Override
  public List<Client> getAll() {
    return clientSpringRepository.findAll()
        .stream()
        .map(clientEntityMapper::entityToDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Client findById(UUID id) {
    return clientEntityMapper.entityToDomain(clientSpringRepository.findById(id).orElseThrow());
  }
}
