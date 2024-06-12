package com.pragma.microservice1.adapters.driven.jpa.mysql.mapper;

import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.microservice1.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserEntityMapper {
    UserEntity toEntity(User user);
    User toModel(UserEntity entity);
}
