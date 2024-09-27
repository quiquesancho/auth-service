package com.edu.quique.auth_service.repository;

import com.edu.quique.auth_service.models.UserMO;
import com.edu.quique.auth_service.repository.mapper.UserMOMapper;
import lombok.AllArgsConstructor;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LdapRepositoryImpl {

  private LdapTemplate ldapTemplate;
  private UserMOMapper userMOMapper;

  public UserMO loadUserByUsername(String username) {
    LdapQuery query = LdapQueryBuilder.query().where("mail").is(username);
    return ldapTemplate.authenticate(query, "admin", userMOMapper);
  }
}
