package com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperatingSystem {
  WINDOWS("Windows"),
  WINDOWS10("Windows NT 10.0"),
  WINDOWS8("Windows NT 6.2"),
  WINDOWS7("Windows NT 6.1"),
  MACOS("Mac OS X"),
  LINUX("Linux"),
  MAC("Mac"),
  UNIX("X11"),
  ANDROID("Android"),
  IPHONE("iPhone"),
  IPAD("iPad"),
  IOS("iOS"),
  UNKNOWNOS("Unknown Operating System");
  private final String operatingSystem;
}
