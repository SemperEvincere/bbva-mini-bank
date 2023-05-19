package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import com.bbva.minibank.infrastructure.entities.ClientEntity;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component()
public class ClientEntityMapper {


  public ClientEntity domainToEntity(Client client) {
    return ClientEntity.builder()
        .id(client.getId())
        .firstName(client.getFirstName())
        .lastName(client.getLastName())
        .email(client.getEmail())
        .phone(client.getPhone())
        .address(client.getAddress())
        .accounts(new ArrayList<>())
        .build();
  }



  public Client entityToDomain(ClientEntity clientEntity) {
    Client client = new Client();
    client.setId(clientEntity.getId());
    client.setFirstName(clientEntity.getFirstName());
    client.setLastName(clientEntity.getLastName());
    client.setEmail(clientEntity.getEmail());
    client.setPhone(clientEntity.getPhone());
    client.setAddress(clientEntity.getAddress());
    client.setAccounts(new ArrayList<>());
    for (AccountEntity accountEntity : clientEntity.getAccounts()) {
      client.getAccounts().add(accountEntity.getAccountNumber());
    }

    return client;
  }


}
