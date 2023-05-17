package com.bbva.minibank.application.services;

import com.bbva.minibank.application.usecases.account.IAccountCreateUseCase;
import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.domain.models.enums.CurrencyEnum;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountCreateUseCase {

  @Override
  public List<Account> create(Client client) {
    List<Account> accounts = new ArrayList<>();
    Account accountARS = new Account();
    accountARS.setCurrency(CurrencyEnum.valueOf("ARS"));
    accountARS.setHolders(List.of(client));
    Account accountUSD = new Account();
    accountUSD.setCurrency(CurrencyEnum.valueOf("USD"));
    accountARS.setHolders(List.of(client));
    accounts.add(accountARS);
    accounts.add(accountUSD);

    return accounts;
  }
}
