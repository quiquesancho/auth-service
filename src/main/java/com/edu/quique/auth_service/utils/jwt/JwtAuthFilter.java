package com.edu.quique.auth_service.utils.jwt;

import com.edu.quique.auth_service.models.UserMO;
import com.edu.quique.auth_service.service.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.edu.quique.auth_service.utils.AppConstants.HEADER_AUTHORIZATION_KEY;
import static com.edu.quique.auth_service.utils.AppConstants.TOKEN_BEARER_PREFIX;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private JwtService jwtService;
  private UserServiceImpl userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      String tokenHeader = request.getHeader(HEADER_AUTHORIZATION_KEY);
      if (isValidHeader(tokenHeader)) {
        Claims claims = jwtService.getJwtClaims(tokenHeader);
        if (claims != null) {
          UserMO user = userService.loadUserByUsername(jwtService.extractSubject(claims));
          setAuthentication(user);
          request.setAttribute("X-User-Id", user.getUsername());
        } else {
          SecurityContextHolder.clearContext();
        }
      } else {
        SecurityContextHolder.clearContext();
      }

      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
      return;
    }
  }

  private boolean isValidHeader(String header) {
    return header != null && !header.isBlank() && header.startsWith(TOKEN_BEARER_PREFIX);
  }

  private void setAuthentication(UserMO userMO) {
    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(
            userMO.getUsername(), null, userMO.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
  }
}
