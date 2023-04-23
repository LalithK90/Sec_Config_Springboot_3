package com.springsecurity3withthymeleaf.asset.company.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaidNot {
  PAID("Paid"),
  NOT("Not Paid");
  public final String paidNot;
}
