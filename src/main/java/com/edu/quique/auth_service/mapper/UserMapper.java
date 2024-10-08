package com.edu.quique.auth_service.mapper;

import com.edu.quique.api.model.User;
import com.edu.quique.auth_service.models.RoleMO;
import com.edu.quique.auth_service.models.UserMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "email")
    @Mapping(source = "authorities", target = "roles")
    User toUser(UserMO userMO);


    default String toRole(RoleMO roleMO) {
        return roleMO.getAuthority();
    }
}
