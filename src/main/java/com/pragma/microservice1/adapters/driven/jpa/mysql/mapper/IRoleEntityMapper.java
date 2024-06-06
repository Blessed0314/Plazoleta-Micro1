package com.pragma.microservice1.adapters.driven.jpa.mysql.mapper;

import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.microservice1.domain.model.Role;

public interface IRoleEntityMapper {
    RoleEntity toEntity(Role role);
    Role toModel(RoleEntity entity);
}
