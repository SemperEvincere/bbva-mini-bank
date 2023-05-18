package com.bbva.minibank.application.usecases.client;

import com.bbva.minibank.domain.models.Client;
import java.util.List;
import java.util.UUID;

public interface IClientFindByUseCase {
  List<Client> getAll();

  Client findById(UUID id);
}
