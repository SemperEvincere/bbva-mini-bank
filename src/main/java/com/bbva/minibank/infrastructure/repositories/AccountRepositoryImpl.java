package com.bbva.minibank.infrastructure.repositories;

import com.bbva.minibank.application.repository.IAccountRepository;
import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.infrastructure.entities.AccountEntity;
import com.bbva.minibank.infrastructure.mappers.AccountEntityMapper;
import com.bbva.minibank.infrastructure.repositories.springdatajpa.IAccountSpringRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements IAccountRepository {

  @Autowired
  private IAccountSpringRepository accountSpringRepository;
  @Autowired
  private AccountEntityMapper accountEntityMapper;

  @Override
  public Account findByAccountNumber(UUID accountNumber) {
    Optional<AccountEntity> accountEntity = accountSpringRepository.findById(accountNumber);
    return accountEntity.map(entity -> accountEntityMapper.entityToDomain(entity)).orElse(null);
  }
}
