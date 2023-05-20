package com.bbva.minibank.presentation.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionDepositResponse {

  private String id;
  private String type;
  private String amount;
  private String accountNumberTo;
  private String clientFullName;
  private String createdAt;
}
