package com.springsecurity3withthymeleaf.asset.common_asset.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StopActive {
  ACTIVE("Active"),
  STOP("Stop");

  private final String stopActive;
}
