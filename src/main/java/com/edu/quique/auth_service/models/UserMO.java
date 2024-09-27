package com.edu.quique.auth_service.models;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMO {
    private String username;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private List<String> roles;

}
