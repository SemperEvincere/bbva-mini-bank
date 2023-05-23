package com.bbva.minibank.infrastructure.repositories.springdatajpa;

import com.bbva.minibank.infrastructure.entities.ClientEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientSpringRepository extends JpaRepository<ClientEntity, UUID> {

  boolean existsByEmail(String email);

  boolean existsByEmailAndLastNameAndFirstName(String email,
      String lastName,
      String firstName);
}
