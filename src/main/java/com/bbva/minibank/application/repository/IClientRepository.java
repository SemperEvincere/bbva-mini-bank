package com.bbva.minibank.application.repository;

import com.bbva.minibank.domain.models.Client;
import org.springframework.stereotype.Repository;

public interface IClientRepository {

  Client saveClient(Client client);

}
