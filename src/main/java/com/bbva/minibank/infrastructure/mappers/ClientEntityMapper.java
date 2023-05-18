package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import com.bbva.minibank.infrastructure.entities.ClientEntity;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component()
public class ClientEntityMapper {


  public ClientEntity domainToEntity(Client client) {
    ClientEntity clientEntity = new ClientEntity();
    clientEntity.setFirstName(client.getFirstName());
    clientEntity.setLastName(client.getLastName());
    clientEntity.setEmail(client.getEmail());
    clientEntity.setPhone(client.getPhone());
    clientEntity.setAddress(client.getAddress());
    clientEntity.setAccounts(new ArrayList<>());

    return clientEntity;
  }



  public Client entityToDomain(ClientEntity clientEntity) {
    Client client = new Client();
    client.setId(clientEntity.getId());
    client.setFirstName(clientEntity.getFirstName());
    client.setLastName(clientEntity.getLastName());
    client.setEmail(clientEntity.getEmail());
    client.setPhone(clientEntity.getPhone());
    client.setAddress(clientEntity.getAddress());

    for (AccountEntity accountEntity : clientEntity.getAccounts()) {
      client.getAccounts().add(accountEntity.getAccountNumber());
    }

    return client;
  }


}
