package com.bbva.minibank.domain.models;

import com.bbva.minibank.domain.models.enums.ClientTypeEnum;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Client {

  private UUID id;
  private ClientTypeEnum type;
  private String lastName;
  private String firstName;
  private String email;
  private String phone;
  private String address;
  private List<UUID> accounts;

  public void addAccount(UUID newAccount) {
    this.accounts.add(newAccount);
  }
}
