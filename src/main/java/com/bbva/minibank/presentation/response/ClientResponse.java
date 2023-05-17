package com.bbva.minibank.presentation.response;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import java.util.List;
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
