package com.bbva.minibank.infrastructure.repositories;

import com.bbva.minibank.application.repository.IClientRepository;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import com.bbva.minibank.infrastructure.entities.ClientEntity;
import com.bbva.minibank.infrastructure.mappers.AccountEntityMapper;
import com.bbva.minibank.infrastructure.mappers.ClientEntityMapper;
import com.bbva.minibank.infrastructure.repositories.springdatajpa.IClientSpringRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements IClientRepository {

  private final IClientSpringRepository clientSpringRepository;
  private final ClientEntityMapper clientEntityMapper;
  private final AccountEntityMapper accountEntityMapper;
  private final AccountRepositoryImpl accountFindUseCase;


  @Override
  public Client saveClient(Client client) {
    ClientEntity clientEntity = clientEntityMapper.domainToEntity(client);
    List<AccountEntity> accountEntities = client.getAccounts().stream().map(accountNumber -> accountEntityMapper.domainToEntity(accountFindUseCase.findByAccountNumber(accountNumber))).collect(Collectors.toList());
    clientEntity.setAccounts(accountEntities);

    clientSpringRepository.save(clientEntity);

    return clientEntityMapper.entityToDomain(clientEntity);
  }

  @Override
  public List<Client> getAll() {
    return clientSpringRepository.findAll().stream().map(clientEntityMapper::entityToDomain).collect(Collectors.toList());
  }

  @Override
  public Optional<Client> findById(UUID id) {
    Optional<ClientEntity> optionalClient = clientSpringRepository.findById(id);
    if (optionalClient.isEmpty()) {
      return Optional.empty();
    }

    return optionalClient.map(clientEntityMapper::entityToDomain);
  }

  @Override
  public boolean existsByEmail(String email) {
    return clientSpringRepository.existsByEmail(email);
  }

  @Override
  public boolean existsByEmailAndLastNameAndFirstName(String email,
      String lastName,
      String firstName) {
    return clientSpringRepository.existsByEmailAndLastNameAndFirstName(email, lastName, firstName);
  }

  @Override
  @Transactional
  public void update(Client client) {
    ClientEntity clientEntity = clientEntityMapper.domainToEntity(client);
    List<AccountEntity> accountEntities = client.getAccounts().stream().map(accountNumber -> accountEntityMapper.domainToEntity(accountFindUseCase.findByAccountNumber(accountNumber))).collect(Collectors.toList());

    clientEntity.setAccounts(accountEntities);

    clientSpringRepository.save(clientEntity);
  }
}
