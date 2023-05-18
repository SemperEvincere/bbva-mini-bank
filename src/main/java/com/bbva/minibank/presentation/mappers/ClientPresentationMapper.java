package com.bbva.minibank.presentation.mappers;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.presentation.request.client.ClientCreateRequest;
import com.bbva.minibank.presentation.response.account.AccountResponse;
import com.bbva.minibank.presentation.response.client.ClientAllDataResponse;
import com.bbva.minibank.presentation.response.client.ClientResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ClientPresentationMapper {

  public ClientResponse domainToResponse(Client client) {
      return ClientResponse.builder()
          .id(client.getId())
          .firstName(client.getFirstName())
          .lastName(client.getLastName())
          .email(client.getEmail())
          .phone(client.getPhone())
          .address(client.getAddress())
          .build();
    }

  public Client requestToDomain(ClientCreateRequest request) {
    Client client = new Client();
    client.setFirstName(request.getFirstName());
    client.setLastName(request.getLastName());
    client.setEmail(request.getEmail());
    client.setPhone(request.getPhone());
    client.setAddress(request.getAddress());
    client.setAccounts(new ArrayList<>());
    return client;
  }

  public ClientAllDataResponse domainToAllDataResponse(Client client, List<AccountResponse> accountResponse) {
    return ClientAllDataResponse.builder()
        .id(client.getId())
        .firstName(client.getFirstName())
        .lastName(client.getLastName())
        .email(client.getEmail())
        .phone(client.getPhone())
        .address(client.getAddress())
        .accounts(accountResponse)
        .build();
  }
}
