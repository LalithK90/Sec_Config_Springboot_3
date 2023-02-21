package com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Browser {
  FIREFOX("Firefox"),
  EDGE("Microsoft Edge"),
  CHROME("Chrome"),
  SAFARI("Safari"),
  OPERA("Opera"),
  MSIE("MSIE"),
  TRIDENT("Trident"),
  INTERNETEXPLORER("Internet Explorer"),
  UNKNOWNBROWSER("Unknown Browser");
  private final String browser;
}
