package com.bbva.minibank.infrastructure.repositories.springdatajpa;

import com.bbva.minibank.infrastructure.entities.TransactionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionSpringRepository extends JpaRepository<TransactionEntity, UUID> {

}
