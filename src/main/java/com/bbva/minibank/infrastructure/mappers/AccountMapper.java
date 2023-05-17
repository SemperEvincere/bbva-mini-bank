package com.bbva.minibank.infrastructure.mappers;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

  @Autowired
  private ClientMapper clientMapper;
  @Autowired
  private TransactionMapper transactionMapper;

  public AccountEntity mapToEntity(Account account) {
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setAccountNumber(account.getAccountNumber());
    accountEntity.setBalance(account.getBalance());
    accountEntity.setCurrency(account.getCurrency());
    accountEntity.setHolders
        (account.getHolders()
            .stream()
            .map(client -> clientMapper.toEntity(client))
            .collect(Collectors.toList()));
    if(account.getTransactions().isEmpty()) {
      accountEntity.setTransactions(null);
    } else {
      accountEntity.setTransactions
          (account.getTransactions()
              .stream()
              .map(transaction -> transactionMapper.toEntity(transaction))
              .collect(Collectors.toList()));
    }

    return null;
  }

}
