package com.bbva.minibank.application.usecases.account;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;
import java.util.List;

public interface IAccountCreateUseCase {

  List<Account> create(Client client);

}
