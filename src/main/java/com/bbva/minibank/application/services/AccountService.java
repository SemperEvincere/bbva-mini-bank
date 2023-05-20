package com.bbva.minibank.application.services;

import com.bbva.minibank.application.repository.IAccountRepository;
import com.bbva.minibank.application.usecases.account.IAccountCreateUseCase;
import com.bbva.minibank.application.usecases.account.IAccountFindUseCase;
import com.bbva.minibank.application.usecases.account.IAccountUpdateUseCase;
import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.enums.CurrencyEnum;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService
    implements
    IAccountCreateUseCase,
    IAccountFindUseCase,
    IAccountUpdateUseCase {

  @Autowired
  private IAccountRepository accountRepository;

  @Override
  public List<Account> create() {
    List<Account> accountsDefault = new ArrayList<>();
    Account accountARS = new Account();
    accountARS.setCurrency(CurrencyEnum.valueOf("ARS"));
    accountARS.setBalance(BigDecimal.valueOf(0.0));
    accountARS.setHolders(new ArrayList<UUID>());
    Account accountUSD = new Account();
    accountUSD.setCurrency(CurrencyEnum.valueOf("USD"));
    accountUSD.setBalance(BigDecimal.valueOf(0.0));
    accountUSD.setHolders(new ArrayList<UUID>());
    accountsDefault.add(accountARS);
    accountsDefault.add(accountUSD);

    return accountsDefault;
  }

  @Override
  public void saveAll(List<Account> accountsDefault) {
    accountRepository.saveAll(accountsDefault);
  }

  @Override
  public Account findByAccountNumber(UUID accountNumber) {
    return accountRepository.findByAccountNumber(accountNumber);
  }

  @Override
  public Account update(Account accountUpdate) {
    return accountRepository.save(accountUpdate);
  }


}