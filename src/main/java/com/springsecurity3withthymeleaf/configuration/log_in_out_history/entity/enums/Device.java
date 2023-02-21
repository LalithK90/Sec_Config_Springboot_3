package com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Device {
  ANDROID("Android"),
  WINDOWSPHONE("Windows Phone"),
  WINDOWS("Windows"),
  WINDOWSDESKTOP("Windows NT"),
  IPHONE("iPhone"),
  IPAD("iPad"),
  MAC("Macintosh"),
  MAXOSCOMPUTER("Mac OS X"),
  MOBILEDEVICE("Mobile"),
  TABLETDEVICE("Tablet"),
  LINUX("Linux"),
  UNKNOWNDEVICE("Unknown Device");
  private final String device;
}
