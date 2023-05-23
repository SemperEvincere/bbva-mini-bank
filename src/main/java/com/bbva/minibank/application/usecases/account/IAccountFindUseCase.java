package com.bbva.minibank.application.usecases.account;

import com.bbva.minibank.domain.models.Account;
import java.util.UUID;

public interface IAccountFindUseCase {

  Account findByAccountNumber(UUID accountNumber);

}
