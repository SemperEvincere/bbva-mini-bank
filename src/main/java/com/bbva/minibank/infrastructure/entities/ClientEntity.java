package com.bbva.minibank.infrastructure.entities;

import com.bbva.minibank.domain.models.enums.ClientTypeEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientEntity {

  @Id
  private UUID id;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false, unique = true)
  @Email
  private String email;

  @Nullable
  private String phone;

  @Nullable
  private String address;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
        name = "client_account",
        joinColumns = @JoinColumn(name = "client_id"),
        inverseJoinColumns = @JoinColumn(name = "account_number")
  )
  private Set<AccountEntity> accounts;

}
