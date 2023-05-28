package com.bbva.minibank.presentation.mappers;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.presentation.request.client.ClientCreateRequest;
import com.bbva.minibank.presentation.response.account.AccountResponse;
import com.bbva.minibank.presentation.response.client.ClientAllDataResponse;
import com.bbva.minibank.presentation.response.client.ClientResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ClientPresentationMapper {

  public ClientResponse domainToResponse(Client client) {
    return ClientResponse
            .builder()
            .id(client.getId())
            .firstName(client.getFirstName())
            .lastName(client.getLastName())
            .email(client.getEmail())
            .phone(client.getPhone())
            .address(client.getAddress())
            .build();
  }

  public Client requestToDomain(ClientCreateRequest request) {
    return Client.builder()
            .id(UUID.randomUUID())
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .phone(request.getPhone())
            .address(request.getAddress())
            .accounts(new ArrayList<UUID>())
            .build();
  }

  public ClientAllDataResponse domainToAllDataResponse(Client client,
      List<AccountResponse> accountResponse) {

    return ClientAllDataResponse
            .builder()
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
