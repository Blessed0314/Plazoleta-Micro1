package com.pragma.microservice1.adapters.driving.http.mapper;

import com.pragma.microservice1.adapters.driving.http.dto.request.LoginUserRequest;
import com.pragma.microservice1.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ILoginUserRequestMapper {
    User addRequestToLoginUser(LoginUserRequest loginUserRequest);
}
