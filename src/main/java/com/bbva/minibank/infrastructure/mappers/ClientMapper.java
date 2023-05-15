package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.entities.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
}
