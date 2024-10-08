package com.edu.quique.auth_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Value("${config.ldap.user-search-filter}")
  private String USER_SEARCH_FILTER;

  @Value("${config.ldap.user-search-base}")
  private String USER_SEARCH_BASE;

  @Value("${config.ldap.group-search-filter}")
  private String GROUP_SEARCH_FILTER;

  @Value("${config.ldap.group-search-base}")
  private String GROUP_SEARCH_BASE;

  @Value("${config.ldap.url}")
  private String URL;

  @Value("${config.ldap.admin-user}")
  private String USER;

  @Value("${config.ldap.admin-pass}")
  private String PASS;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests(
            req -> req.requestMatchers("/auth/login").permitAll().anyRequest().authenticated())
        .sessionManagement(
            manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(ldapAuthenticationProvider());
    return httpSecurity.build();
  }

  @Bean
  public DefaultSpringSecurityContextSource contextSource() {
    DefaultSpringSecurityContextSource contextSource =
        new DefaultSpringSecurityContextSource(Collections.singletonList(URL), USER_SEARCH_BASE);
    contextSource.setUserDn(USER);
    contextSource.setPassword(PASS);
    return contextSource;
  }

  @Bean
  public LdapAuthenticationProvider ldapAuthenticationProvider() {
    BindAuthenticator authenticator = new BindAuthenticator(contextSource());
    authenticator.setUserDnPatterns(new String[] {USER_SEARCH_FILTER});
    return new LdapAuthenticationProvider(authenticator);
  }
}
