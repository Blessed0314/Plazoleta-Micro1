package com.pragma.microservice1.adapters.driving.http.dto.request;


import com.pragma.microservice1.adapters.driving.http.util.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
public class AddRoleRequest {
    @NotEmpty(message = MessageConstants.FIELD_NAME_NULL_MESSAGE)
    private final String name;
    @NotBlank(message = MessageConstants.FIELD_DESCRIPTION_NULL_MESSAGE)
    private final String description;
}
