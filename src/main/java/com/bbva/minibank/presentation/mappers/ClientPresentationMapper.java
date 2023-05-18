package com.bbva.minibank.presentation.mappers;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.presentation.request.client.ClientCreateRequest;
import com.bbva.minibank.presentation.response.client.ClientResponse;
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
    return client;
  }
}
