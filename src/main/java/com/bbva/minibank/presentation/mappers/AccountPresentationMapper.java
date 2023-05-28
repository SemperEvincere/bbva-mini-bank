package com.bbva.minibank.presentation.mappers;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.presentation.response.account.AccountCreateResponse;
import com.bbva.minibank.presentation.response.account.AccountResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class AccountPresentationMapper {

  public List<AccountResponse> domainToResponseList(List<Account> accounts) {
    Set<AccountResponse> accountResponses = new HashSet<>();
    for (Account account : accounts) {
      accountResponses.add(domainToResponse(account));
    }
    return accountResponses.stream().toList();
  }

  private AccountResponse domainToResponse(Account account) {
    return AccountResponse.builder().id(account.getAccountNumber()).balance(account.getBalance()).currency(account.getCurrency()).build();
  }

  public AccountCreateResponse domainToCreateResponse(Account account) {
    return AccountCreateResponse.builder().accountId(account.getAccountNumber()).holderId(account.getClientHolder()).secondHolderId(account.getClientSecondHolder()).balance(account.getBalance()).currency(account.getCurrency()).build();
  }
}
