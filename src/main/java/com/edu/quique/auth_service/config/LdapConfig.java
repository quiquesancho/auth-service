package com.edu.quique.auth_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@EnableLdapRepositories
public class LdapConfig {

    @Value("${config.ldap.url}")
    private String URL;
    @Value("${config.ldap.user-search-base}")
    private String USER_SEARCH_BASE;
    @Value("${config.ldap.admin-user}")
    private String USER;
    @Value("${config.ldap.admin-pass}")
    private String PASS;

    @Bean
    public LdapContextSource getLdapContextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(URL);
        contextSource.setBase(USER_SEARCH_BASE);
        contextSource.setUserDn(USER);
        contextSource.setPassword(PASS);
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(getLdapContextSource());
    }
}
