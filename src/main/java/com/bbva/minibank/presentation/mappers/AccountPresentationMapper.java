package com.bbva.minibank.presentation.mappers;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.presentation.response.account.AccountResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AccountPresentationMapper {

  public List<AccountResponse> domainToResponseList(List<Account> accounts) {
    List<AccountResponse> accountResponses = new ArrayList<>();
    for (Account account : accounts) {
      accountResponses.add(domainToResponse(account));
    }
    return accountResponses;
  }

  private AccountResponse domainToResponse(Account account) {
    return AccountResponse.builder()
        .id(account.getAccountNumber())
        .balance(account.getBalance())
        .currency(account.getCurrency().name())
        .build();
  }
}
