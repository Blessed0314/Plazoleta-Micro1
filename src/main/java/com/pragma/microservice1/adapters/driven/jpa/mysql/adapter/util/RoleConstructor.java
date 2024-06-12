package com.pragma.microservice1.adapters.driven.jpa.mysql.adapter.util;

import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.microservice1.domain.model.Role;

public class RoleConstructor {

    public RoleEntity defaultRoleBuilder(String roleName){
        return RoleEntity.fromEnum(RoleEnum.valueOf(roleName));
    }
    public Role roleBuilder(Role role) {
        return new Role(
                role.getId(),
                role.getName().trim().toUpperCase(),
                role.getDescription()
        );
    }
}