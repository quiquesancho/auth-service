package com.edu.quique.auth_service.controllers;

import com.edu.quique.api.AuthApi;
import com.edu.quique.api.model.LoginRequest;
import com.edu.quique.api.model.LoginResponse;
import com.edu.quique.auth_service.mapper.UserMapper;
import com.edu.quique.auth_service.models.UserMO;
import com.edu.quique.auth_service.service.UserServiceImpl;
import com.edu.quique.auth_service.utils.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@Slf4j
public class LoginController implements AuthApi {

  private UserServiceImpl userService;
  private JwtService jwtService;
  private UserMapper userMapper;


  public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
    log.info("POST /auth/login {}", loginRequest.getUsername());
    UserMO userMO =
        userService.loadUserByUsername(loginRequest.getUsername(), loginRequest.getPassword());
    LoginResponse res = new LoginResponse();
    res.setUser(userMapper.toUser(userMO));
    res.setToken(jwtService.getJWTToken(userMO));
    return ResponseEntity.ok(res);
  }
}
