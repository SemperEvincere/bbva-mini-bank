package com.bbva.minibank.application.repository;

import com.bbva.minibank.domain.models.Account;
import java.util.UUID;

public interface IAccountRepository {

  Account findByAccountNumber(UUID accountNumber);
}
