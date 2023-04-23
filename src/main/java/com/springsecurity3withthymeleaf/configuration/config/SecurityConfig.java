package com.springsecurity3withthymeleaf.configuration.config;


import com.springsecurity3withthymeleaf.asset.user.service.UserService;
import com.springsecurity3withthymeleaf.configuration.config.custom_handlers.CustomAuthenticationFailureHandler;
import com.springsecurity3withthymeleaf.configuration.config.custom_handlers.CustomAuthenticationSuccessHandler;
import com.springsecurity3withthymeleaf.configuration.config.custom_handlers.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final String[] ALL_PERMIT_URL = {
            "/webjars/**", "/css/**", "/js/**", "/img/**", "/app/forgottenPassword",
            "/app/privacyPolicy", "/app/publicTerms",
            "/7c3cc16d7cf0045498e4ad876423dd3f.txt", "/ads.txt", "/favicon.ico", "/sitemap.xml",
            "/login", "/index", "/add", "/*",
            "/select/**", "/aliasExists/**", "/app/register/**",
    };


    private final UserService userDetailsService;

    public SecurityConfig(@Lazy UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

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
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) ->
                        requests
                                .requestMatchers(ALL_PERMIT_URL).permitAll()
                                .requestMatchers("/userDetails/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/user/**").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
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

                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessHandler(customLogoutSuccessHandler())
                                .invalidateHttpSession(true)
                                .permitAll()
                )
                .oauth2Login((oauth2) -> {
                            oauth2
                                    .loginPage("/login")
                                    .successHandler(customAuthenticationSuccessHandler());
                        }

                )
                .securityContext(
                        (securityContext) ->
                                securityContext.requireExplicitSave(false))
                //Cross site disable
                .exceptionHandling().accessDeniedPage("/error/403")
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

//  @Bean
//  public HttpSessionIdResolver httpSessionIdResolver() {
//    return HeaderHttpSessionIdResolver.xAuthToken();
//  }

/*
logout().deleteCookies("remove").invalidateHttpSession(false)
                  .logoutUrl("/custom-logout").logoutSuccessUrl("/logout-success");
          return http.build();
* */

}

