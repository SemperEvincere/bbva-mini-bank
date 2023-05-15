package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.entities.ClientEntity;
import com.bbva.minibank.presentation.request.ClientCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

  public ClientEntity toEntity(Client client) {
    ClientEntity clientEntity = new ClientEntity();
    clientEntity.setFirstName(client.getFirstName());
    clientEntity.setLastName(client.getLastName());
    clientEntity.setEmail(client.getEmail());
    clientEntity.setPhone(client.getPhone());
    clientEntity.setAddress(client.getAddress());
    // todo: map accounts  List<AccountEntity> accountEntities = mapAccounts(client.getAccounts());
    if (client.getCoHolder() != null) {
      ClientEntity coHolderEntity = toEntity(client.getCoHolder());
      clientEntity.setCoHolder(coHolderEntity);
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
    client.setFirstName(clientEntity.getFirstName());
    client.setLastName(clientEntity.getLastName());
    client.setEmail(clientEntity.getEmail());
    client.setPhone(clientEntity.getPhone());
    client.setAddress(clientEntity.getAddress());
    return client;
  }
}
