package com.bbva.minibank.domain.models;

import com.bbva.minibank.domain.models.enums.CurrencyEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
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
@NoArgsConstructor
@Builder
public class Account {

  @NotNull
  private UUID accountNumber;
  @PositiveOrZero
  private BigDecimal balance;
  @Enumerated(EnumType.STRING)
  @NotNull
  private CurrencyEnum currency;
  @NotNull
  private UUID clientHolder;
  @Nullable
  private UUID clientSecondHolder;

  private List<Transaction> transactions;



}
