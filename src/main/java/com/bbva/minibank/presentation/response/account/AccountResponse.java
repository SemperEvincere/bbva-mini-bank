package com.bbva.minibank.presentation.response.account;

import java.util.UUID;
import lombok.Builder;

@Builder
public class AccountResponse {

  private UUID id;
  private String type;
  private String currency;

}
