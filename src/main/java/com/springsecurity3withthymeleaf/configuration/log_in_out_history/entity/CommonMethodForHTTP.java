package com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity;

import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.enums.Browser;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.enums.Device;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.enums.OperatingSystem;
import jakarta.servlet.http.Cookie;

public class CommonMethodForHTTP {

  public Device extractDevice(String userAgent) {

    if ( userAgent.contains(Device.ANDROID.getDevice()) ) {
      return Device.ANDROID;
    } else if ( userAgent.contains(Device.IPHONE.getDevice()) ) {
      return Device.IPHONE;
    } else if ( userAgent.contains(Device.IPAD.getDevice()) ) {
      return Device.IPAD;
    } else if ( userAgent.contains(Device.MAXOSCOMPUTER.getDevice()) ) {
      return Device.MAXOSCOMPUTER;
    } else if ( userAgent.contains(Device.MAC.getDevice()) ) {
      return Device.MAC;
    } else if ( userAgent.contains(Device.WINDOWSPHONE.getDevice()) ) {
      return Device.WINDOWSPHONE;
    } else if ( userAgent.contains(Device.WINDOWSDESKTOP.getDevice()) ) {
      return Device.WINDOWSDESKTOP;
    } else if ( userAgent.contains(Device.WINDOWS.getDevice()) ) {
      return Device.WINDOWS;
    } else if ( userAgent.contains(Device.LINUX.getDevice()) ) {
      return Device.LINUX;
    } else if ( userAgent.contains(Device.MOBILEDEVICE.getDevice()) ) {
      return Device.MOBILEDEVICE;
    } else if ( userAgent.contains(Device.TABLETDEVICE.getDevice()) ) {
      return Device.TABLETDEVICE;

    } else {
      return Device.UNKNOWNDEVICE;
    }
  }

  public String extractCookies(Cookie[] cookies) {
    if ( cookies == null ) {
      return "";
    }
    StringBuilder cookieString = new StringBuilder();
    for ( Cookie cookie : cookies ) {
      cookieString.append(cookie.getName());
      cookieString.append("=");
      cookieString.append(cookie.getValue());
      cookieString.append(";");
    }
    return cookieString.toString();
  }

  public Browser extractBrowser(String userAgent) {
    System.out.println(userAgent);
    if ( userAgent.contains(Browser.FIREFOX.getBrowser()) ) {
      return Browser.FIREFOX;
    } else if ( userAgent.contains(Browser.EDGE.getBrowser()) ) {
      return Browser.EDGE;
    } else if ( userAgent.contains(Browser.CHROME.getBrowser()) ) {
      return Browser.CHROME;
    } else if ( userAgent.contains(Browser.SAFARI.getBrowser()) ) {
      return Browser.SAFARI;
    } else if ( userAgent.contains(Browser.OPERA.getBrowser()) ) {
      return Browser.OPERA;
    } else if ( userAgent.contains(Browser.MSIE.getBrowser()) || userAgent.contains(Browser.TRIDENT.getBrowser()) ) {
      return Browser.INTERNETEXPLORER;
    }
    return Browser.UNKNOWNBROWSER;
  }

  public OperatingSystem extractOperatingSystem(String userAgent) {
    // Code to extract operating system information from user agent string
    if ( userAgent.contains(OperatingSystem.WINDOWS.getOperatingSystem()) ) {
      return OperatingSystem.WINDOWS;
    } else if ( userAgent.contains(OperatingSystem.WINDOWS10.getOperatingSystem()) ) {
      return OperatingSystem.WINDOWS10;
    } else if ( userAgent.contains(OperatingSystem.WINDOWS8.getOperatingSystem()) ) {
      return OperatingSystem.WINDOWS8;
    } else if ( userAgent.contains(OperatingSystem.WINDOWS7.getOperatingSystem()) ) {
      return OperatingSystem.WINDOWS7;
    } else if ( userAgent.contains(OperatingSystem.MACOS.getOperatingSystem()) ) {
      return OperatingSystem.MACOS;
    } else if ( userAgent.contains(OperatingSystem.LINUX.getOperatingSystem()) ) {
      return OperatingSystem.LINUX;
    } else if ( userAgent.contains(OperatingSystem.MAC.getOperatingSystem()) ) {
      return OperatingSystem.MAC;
    } else if ( userAgent.contains(OperatingSystem.UNIX.getOperatingSystem()) ) {
      return OperatingSystem.UNIX;
    } else if ( userAgent.contains(OperatingSystem.ANDROID.getOperatingSystem()) ) {
      return OperatingSystem.ANDROID;
    } else if ( userAgent.contains(OperatingSystem.IPHONE.getOperatingSystem()) ){
      return OperatingSystem.IOS;
    }
    else if ( userAgent.contains(OperatingSystem.IPAD.getOperatingSystem()) ) {
      return OperatingSystem.IOS;
    }
    return OperatingSystem.UNKNOWNOS;
  }
}
