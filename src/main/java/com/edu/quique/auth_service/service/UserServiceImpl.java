package com.edu.quique.auth_service.service;

import com.edu.quique.auth_service.models.UserMO;
import com.edu.quique.auth_service.repository.LdapRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl {

  private LdapRepositoryImpl ldapRepository;

  public UserMO loadUserByUsername(String username, String password) {
    return ldapRepository.loadUserByUsername(username, password);
  }

  public UserMO loadUserByUsername(String username) throws UsernameNotFoundException {
    return ldapRepository.loadUserByUsername(username);
  }
}
