package com.pragma.microservice1.adapters.driving.http.mapper;

import com.pragma.microservice1.adapters.driving.http.dto.response.LoginUserResponse;
import com.pragma.microservice1.domain.model.BodyAuth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ILoginUserResponseMapper {
    LoginUserResponse toLoginUserResponse(BodyAuth bodyAuth);
}
