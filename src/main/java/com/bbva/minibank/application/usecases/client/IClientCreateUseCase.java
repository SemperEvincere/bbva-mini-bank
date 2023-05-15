package com.bbva.minibank.application.usecases.client;

import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.presentation.request.ClientCreateRequest;

public interface IClientCreateUseCase {

      Client create(ClientCreateRequest request);
}
