package com.bbva.minibank.application.usecases.client;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.domain.models.Client;

public interface IClientUpdateUseCase {

  Client update(Client holder);

  void addAccount(Client holder, Account account);
}
