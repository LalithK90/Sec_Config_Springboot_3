package com.springsecurity3withthymeleaf.configuration.config;


import com.springsecurity3withthymeleaf.configuration.custom_handlers.CustomAuthenticationFailureHandler;
import com.springsecurity3withthymeleaf.configuration.custom_handlers.CustomAuthenticationSuccessHandler;
import com.springsecurity3withthymeleaf.configuration.custom_handlers.CustomLogoutSuccessHandler;
import com.springsecurity3withthymeleaf.configuration.user_session_log.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final String[] ALL_PERMIT_URL = {"/","/docs/**", "/resources/**", "/static/**", "/webjars/**",
      "/login", "/select/**", "/index", "/register/**", "/forgottenPassword",};

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

// login relate bean - start
  @Bean
  public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
    return new CustomAuthenticationSuccessHandler();
  }

  @Bean
  public LogoutSuccessHandler customLogoutSuccessHandler() {
    return new CustomLogoutSuccessHandler();
  }


  @Bean
  public AuthenticationFailureHandler customAuthenticationFailureHandler() {
    return new CustomAuthenticationFailureHandler();
  }
//  login related bean - end

  @Bean
  public UserDetailsServiceImpl userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((requests) ->
                                   requests
                                       .requestMatchers(ALL_PERMIT_URL).permitAll()
                                       .requestMatchers("/sample/**").hasRole("USER")
                                       .requestMatchers("/sample_two/**").hasAnyRole("USER", "ADMIN")
                                       .anyRequest().authenticated()
                              )
        .formLogin((form) ->
                       form
                           .loginPage("/login")
                           .loginProcessingUrl("/login")
                           .failureUrl("/login?error")
                           .failureHandler(customAuthenticationFailureHandler())
                           .successHandler(customAuthenticationSuccessHandler())
                           .permitAll()
                  )
        .logout((logout) ->
                    logout
                        .logoutSuccessHandler(customLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
               )
        //remember me
   /*     .rememberMe((remember) ->
                        remember
                            .userDetailsService(userDetailsService())
                            .key("uniqueAndSecret")
                            .useSecureCookie(true)
                            .tokenValiditySeconds(43200) // token is valid 12 hours

                   )*/
//session management
        .sessionManagement(
            (sessionManagement) ->
                sessionManagement
                    .sessionFixation()
                    .migrateSession()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .sessionConcurrency((sessionConcurrency) ->
                                            sessionConcurrency
                                                .maximumSessions(1)
                                                .expiredUrl("/login?invalid-session=true")
                                                .maxSessionsPreventsLogin(true)
                                                .sessionRegistry(sessionRegistry())
                                       ))
        .securityContext(
            (securityContext) ->
                securityContext.requireExplicitSave(false))
        //Cross site disable
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling()
        .and()
        .headers()
        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));

    return http.build();

  }

//  Session management -bean start

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  @Bean
  public HttpSessionIdResolver httpSessionIdResolver() {
    return HeaderHttpSessionIdResolver.xAuthToken();
  }

//  Session management -bean end

  //todo : session management and remember me was not working properly

}

