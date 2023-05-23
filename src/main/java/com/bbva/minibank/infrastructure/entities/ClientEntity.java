package com.bbva.minibank.infrastructure.entities;

import com.bbva.minibank.domain.models.enums.ClientTypeEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.util.List;
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

  private ClientTypeEnum type;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<AccountEntity> accounts;

}
