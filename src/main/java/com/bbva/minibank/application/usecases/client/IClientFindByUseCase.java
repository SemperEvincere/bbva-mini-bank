package com.bbva.minibank.application.usecases.client;

import com.bbva.minibank.domain.models.Client;
import java.util.List;

public interface IClientFindByUseCase {
  List<Client> getAll();
}
