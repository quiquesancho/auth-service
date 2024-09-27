package com.edu.quique.auth_service.repository.mapper;

import com.edu.quique.auth_service.exceptions.UserLdapNotFoundException;
import com.edu.quique.auth_service.models.UserMO;
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

@Service
public class UserMOMapper implements AuthenticatedLdapEntryContextMapper<UserMO> {

  @Override
  public UserMO mapWithContext(
      DirContext dirContext, LdapEntryIdentification ldapEntryIdentification) {
    try {
      SearchControls controls = new SearchControls();
      controls.setSearchScope(SearchControls.OBJECT_SCOPE);

      NamingEnumeration<SearchResult> searchResult =
          dirContext.search(ldapEntryIdentification.getRelativeName(), "(objectClass=*)", controls);

      Attributes attributes = searchResult.next().getAttributes();

      String email = (String) attributes.get("mail").get();
      var ous = attributes.get("ou").getAll();
      List<String> roles = new ArrayList<>();
      while (ous.hasMoreElements()) {
        roles.add((String) ous.next());
      }

      // Construir y devolver el objeto UserMO con los datos mapeados
      return UserMO.builder().username(email).roles(roles).build();

    } catch (NamingException e) {
      throw new UserLdapNotFoundException("Error al extraer los atributos del usuario LDAP", e);
    }
  }
}
