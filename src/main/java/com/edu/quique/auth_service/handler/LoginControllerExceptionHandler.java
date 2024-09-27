package com.edu.quique.auth_service.handler;

import com.edu.quique.auth_service.exceptions.UserLdapNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class LoginControllerExceptionHandler {

  @ExceptionHandler(UserLdapNotFoundException.class)
  public ResponseEntity<String> userLdapNotFoundExceptionHandler(UserLdapNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> genericRuntimeExceptionHandler(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
