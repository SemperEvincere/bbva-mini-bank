package com.bbva.minibank.domain.models.enums;

import jakarta.persistence.Enumerated;
import org.springframework.stereotype.Component;

public enum TransactionTypeEnum {
  DEPOSIT,
  WITHDRAWAL,
  TRANSFER;
}
