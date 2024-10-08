package com.edu.quique.auth_service.controllers;

import com.edu.quique.auth_service.models.LoginRequest;
import com.edu.quique.auth_service.models.LoginResponse;
import com.edu.quique.auth_service.models.UserMO;
import com.edu.quique.auth_service.service.UserServiceImpl;
import com.edu.quique.auth_service.utils.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
@Slf4j
public class LoginController {

  private UserServiceImpl userService;
  private JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    log.info("POST /login {}", loginRequest.toString());
    UserMO userMO =
        userService.loadUserByUsername(loginRequest.getUsername(), loginRequest.getPassword());
    return ResponseEntity.ok(
        LoginResponse.builder().userMO(userMO).token(jwtService.getJWTToken(userMO)).build());
  }
}
