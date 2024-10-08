package com.edu.quique.auth_service.models;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMO implements UserDetails {
    private String username;
    private String password;
    private List<RoleMO> authorities;
}
