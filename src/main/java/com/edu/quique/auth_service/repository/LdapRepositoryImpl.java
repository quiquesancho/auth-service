package com.edu.quique.auth_service.repository;

import com.edu.quique.auth_service.models.UserMO;
import com.edu.quique.auth_service.repository.mapper.UserMOMapper;
import lombok.AllArgsConstructor;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

import static com.edu.quique.auth_service.utils.AppConstants.FIND_ATTRIBUTE;

@Service
@AllArgsConstructor
public class LdapRepositoryImpl {

  private LdapTemplate ldapTemplate;
  private UserMOMapper userMOMapper;

  public UserMO loadUserByUsername(String username, String password) {
    LdapQuery query = LdapQueryBuilder.query().where(FIND_ATTRIBUTE).is(username);
    return ldapTemplate.authenticate(query, password, userMOMapper);
  }

  public UserMO loadUserByUsername(String username) {
    LdapQuery query = LdapQueryBuilder.query().where(FIND_ATTRIBUTE).is(username);
    return ldapTemplate.search(query, userMOMapper).get(0);
  }
}
