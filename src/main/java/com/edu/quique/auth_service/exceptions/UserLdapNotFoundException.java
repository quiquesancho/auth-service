package com.edu.quique.auth_service.exceptions;

public class UserLdapNotFoundException extends RuntimeException {
    public UserLdapNotFoundException(String msg, Throwable e) {
        super(msg, e);
    }
}
