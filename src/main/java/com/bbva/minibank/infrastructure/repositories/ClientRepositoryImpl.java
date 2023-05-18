package com.bbva.minibank.infrastructure.repositories;

import com.bbva.minibank.application.repository.IClientRepository;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.entities.ClientEntity;
import com.bbva.minibank.infrastructure.mappers.ClientMapper;
import com.bbva.minibank.infrastructure.repositories.springdatajpa.IClientSpringRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ClientRepositoryImpl implements IClientRepository  {

  private final IClientSpringRepository clientSpringRepository;
  private final ClientMapper clientMapper;
  @Override
  public Client saveClient(Client client) {
    ClientEntity clientEntity = clientMapper.toEntity(client);
    clientSpringRepository.save(clientEntity);
    return clientMapper.entityToClient(clientEntity);
  }

  @Override
  public List<Client> getAll() {
    return clientSpringRepository.findAll()
        .stream()
        .map(clientMapper::entityToClient)
        .collect(Collectors.toList());
  }

  @Override
  public Client findById(UUID id) {
    ClientEntity clientEntity = clientSpringRepository.findById(id).orElse(null);
    assert clientEntity != null;
    return clientMapper.entityToClient(clientEntity);
  }
}
