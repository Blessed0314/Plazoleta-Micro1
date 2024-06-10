package com.pragma.microservice1.adapters.driving.http.mapper;

import com.pragma.microservice1.adapters.driving.http.dto.request.AddRoleRequest;
import com.pragma.microservice1.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRoleRequestMapper {
    @Mapping(target = "id", ignore = true)
    Role addRequestToRole(AddRoleRequest addRoleRequest);
}
