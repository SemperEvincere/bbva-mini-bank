package com.bbva.minibank.presentation.response.client;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClientResponse {

  private UUID id;
  private String lastName;
  private String firstName;
  private String email;
  private String phone;
  private String address;
}
