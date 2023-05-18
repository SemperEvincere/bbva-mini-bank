package com.bbva.minibank.presentation.mappers;

import com.bbva.minibank.domain.models.Account;
import com.bbva.minibank.presentation.response.account.AccountResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AccountPresentationMapper {

  public List<AccountResponse> domainToResponseList(List<UUID> accounts) {
    return accounts.stream()
        .map(this::domainToResponse)
        .collect(java.util.stream.Collectors.toList());
  }

  private AccountResponse domainToResponse(UUID uuid) {
    return AccountResponse.builder()
        .id(uuid)
        .build();
  }
}
