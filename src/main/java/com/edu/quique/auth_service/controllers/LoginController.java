package com.edu.quique.auth_service.controllers;

import com.edu.quique.auth_service.models.LoginRequest;
import com.edu.quique.auth_service.models.UserMO;
import com.edu.quique.auth_service.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class LoginController {

  private UserServiceImpl userService;

  @PostMapping("/login")
  public ResponseEntity<UserMO> login(@RequestBody LoginRequest loginRequest) {
    userService.loadUserByUsername(loginRequest.getUsername());
    return ResponseEntity.ok(userService.loadUserByUsername(loginRequest.getUsername()));
  }
}
