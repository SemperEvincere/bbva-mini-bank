package com.bbva.minibank.presentation.response.client;

import com.bbva.minibank.presentation.response.account.AccountResponse;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public class ClientAllDataResponse {

  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String address;
  private List<AccountResponse> accounts;

}
