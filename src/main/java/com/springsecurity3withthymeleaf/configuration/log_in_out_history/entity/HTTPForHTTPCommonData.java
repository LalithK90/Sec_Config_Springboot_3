package com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity;

import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.enums.Browser;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.enums.Device;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.enums.OperatingSystem;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@MappedSuperclass
public class HTTPForHTTPCommonData extends CommonMethodForHTTP {

  private String username;

  @Enumerated( EnumType.STRING )
  private Browser browser;

  @Enumerated( EnumType.STRING )
  private OperatingSystem operatingSystem;

  @Enumerated( EnumType.STRING )
  private Device device;

  private String ipAddress;

  private String language;

  private String requestMethod;

  private String sessionId;

  private String cookies;

  private String queryString;

  private String referrer;

  private String requestURI;

  private String
      xforwardedfor,
      proxy_client_ip,
      wl_proxy_client_ip,
      http_x_forwarded_for,
      http_x_forwarded,
      http_x_cluster_client_ip,
      http_client_ip,
      http_forwarded_for,
      http_forwarded,
      http_via;

  public HTTPForHTTPCommonData(HttpServletRequest request) {
    setUsername(request.getParameter("username"));
    setBrowser(extractBrowser(request.getHeader("User-Agent")));
    setOperatingSystem(extractOperatingSystem(request.getHeader("User-Agent")));
    setDevice(extractDevice(request.getHeader("User-Agent")));
    setIpAddress(request.getRemoteAddr());
    setLanguage(request.getHeader("Accept-Language"));
    setRequestMethod(request.getMethod());
    setSessionId(request.getSession().getId());
    setCookies(extractCookies(request.getCookies()));
    setQueryString(request.getQueryString());
    setReferrer(request.getHeader("Referer"));
    setRequestURI(request.getRequestURI());
    this.xforwardedfor = request.getHeader("X-Forwarded-For");
    this.proxy_client_ip = request.getHeader("Proxy-Client-IP");
    this.wl_proxy_client_ip = request.getHeader("WL-Proxy-Client-IP");
    this.http_x_forwarded_for = request.getHeader("HTTP_X_FORWARDED_FOR");
    this.http_x_forwarded = request.getHeader("HTTP_X_FORWARDED");
    this.http_x_cluster_client_ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
    this.http_client_ip = request.getHeader("HTTP_CLIENT_IP");
    this.http_forwarded_for = request.getHeader("HTTP_FORWARDED_FOR");
    this.http_forwarded = request.getHeader("HTTP_FORWARDED");
    this.http_via = request.getHeader("HTTP_VIA");
  }


}
