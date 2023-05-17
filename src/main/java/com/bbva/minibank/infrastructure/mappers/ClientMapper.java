package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import com.bbva.minibank.infrastructure.entities.ClientEntity;
import com.bbva.minibank.presentation.request.client.ClientCreateRequest;
import com.bbva.minibank.presentation.response.client.ClientResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component()
public class ClientMapper {

  @Autowired
  private AccountMapper accountMapper;

  public ClientEntity toEntity(Client client) {
    ClientEntity clientEntity = new ClientEntity();
    clientEntity.setFirstName(client.getFirstName());
    clientEntity.setLastName(client.getLastName());
    clientEntity.setEmail(client.getEmail());
    clientEntity.setPhone(client.getPhone());
    clientEntity.setAddress(client.getAddress());
    List<AccountEntity> accountEntities = client.getAccounts().stream().map(accountMapper::mapToEntity).toList();
    clientEntity.setAccounts(accountEntities);
    if (client.getCoHolder() != null) {
      clientEntity.setCoHolder(toEntity(client.getCoHolder()));
    }
    return clientEntity;
  }

  public Client toClient(ClientCreateRequest request) {
    Client client = new Client();
    client.setFirstName(request.getFirstName());
    client.setLastName(request.getLastName());
    client.setEmail(request.getEmail());
    client.setPhone(request.getPhone());
    client.setAddress(request.getAddress());
    return client;
  }

  public Client entityToClient(ClientEntity clientEntity) {
    Client client = new Client();
    client.setId(clientEntity.getId());
    client.setFirstName(clientEntity.getFirstName());
    client.setLastName(clientEntity.getLastName());
    client.setEmail(clientEntity.getEmail());
    client.setPhone(clientEntity.getPhone());
    client.setAddress(clientEntity.getAddress());
    return client;
  }

  public ClientResponse toResponse(Client client) {
    return ClientResponse.builder()
        .id(client.getId())
        .firstName(client.getFirstName())
        .lastName(client.getLastName())
        .email(client.getEmail())
        .phone(client.getPhone())
        .address(client.getAddress())
        .build();
  }
}
