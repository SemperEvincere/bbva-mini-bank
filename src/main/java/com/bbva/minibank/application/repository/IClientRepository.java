package com.bbva.minibank.application.repository;

import com.bbva.minibank.domain.models.Client;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

public interface IClientRepository {

  Client saveClient(Client client);

  List<Client> getAll();

  Client findById(UUID id);
}
