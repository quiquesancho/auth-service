package com.edu.quique.auth_service.repository.mapper;

import com.edu.quique.auth_service.exceptions.UserLdapNotFoundException;
import com.edu.quique.auth_service.models.RoleMO;
import com.edu.quique.auth_service.models.UserMO;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.AuthenticatedLdapEntryContextMapper;
import org.springframework.ldap.core.LdapEntryIdentification;
import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.List;

import static com.edu.quique.auth_service.utils.AppConstants.*;

@Service
public class UserMOMapper
    implements AuthenticatedLdapEntryContextMapper<UserMO>, AttributesMapper<UserMO> {

  @Override
  public UserMO mapWithContext(
      DirContext dirContext, LdapEntryIdentification ldapEntryIdentification) {
    try {
      SearchControls controls = new SearchControls();
      controls.setSearchScope(SearchControls.OBJECT_SCOPE);

      NamingEnumeration<SearchResult> searchResult =
          dirContext.search(
              ldapEntryIdentification.getRelativeName(), FILTER_RESULT_SEARCH, controls);

      Attributes attributes = searchResult.next().getAttributes();

      String email = (String) attributes.get(FIND_ATTRIBUTE).get();
      var ous = attributes.get(OU).getAll();
      List<RoleMO> authorities = new ArrayList<>();
      while (ous.hasMore()) {
        authorities.add(RoleMO.builder().authority((String) ous.next()).build());
      }

      // Construir y devolver el objeto UserMO con los datos mapeados
      return UserMO.builder().username(email).authorities(authorities).build();

    } catch (NamingException e) {
      throw new UserLdapNotFoundException("Error al extraer los atributos del usuario LDAP", e);
    }
  }

  @Override
  public UserMO mapFromAttributes(Attributes attributes) throws NamingException {
    String email = (String) attributes.get(FIND_ATTRIBUTE).get();
    var ous = attributes.get(OU).getAll();
    List<RoleMO> authorities = new ArrayList<>();
    while (ous.hasMore()) {
      authorities.add(RoleMO.builder().authority((String) ous.next()).build());
    }
    return UserMO.builder().username(email).authorities(authorities).build();
  }
}
