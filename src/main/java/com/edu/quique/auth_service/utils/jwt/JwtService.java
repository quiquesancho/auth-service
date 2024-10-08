package com.edu.quique.auth_service.utils.jwt;

import com.edu.quique.auth_service.models.RoleMO;
import com.edu.quique.auth_service.models.UserMO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@NoArgsConstructor
public class JwtService {

  @Value("${config.privateKey}")
  private String privateKey;

  public static final long TOKEN_EXPIRATION_TIME = 1_800_000;

  public String getJWTToken(UserMO userMO) {
    String token =
        Jwts.builder()
            .setId("")
            .setSubject(userMO.getUsername())
            .claim(
                "authorities", userMO.getAuthorities().stream().map(RoleMO::getAuthority).toList())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
            .signWith(getSigningKey(privateKey), SignatureAlgorithm.HS512)
            .compact();

    return "Bearer " + token;
  }

  private Key getSigningKey(String secret) {
    byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
