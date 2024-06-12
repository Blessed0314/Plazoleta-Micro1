package com.pragma.microservice1.adapters.driving.http.dto.request;


import com.pragma.microservice1.adapters.driving.http.util.MessageConstants;
import com.pragma.microservice1.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class AddUserRequest {
    @NotBlank(message = MessageConstants.FIELD_DNI_NULL_MESSAGE)
    @Pattern(regexp = "^\\d+$", message = MessageConstants.FIELD_DNI_ONLY_NUMBERS_MESSAGE)
    private final String dni;

    @NotBlank(message = MessageConstants.FIELD_NAME_NULL_MESSAGE)
    private final String name;

    @NotBlank(message = MessageConstants.FIELD_LAST_NAME_NULL_MESSAGE)
    private final String lastname;

    @NotBlank(message = MessageConstants.FIELD_CELLPHONE_NULL_MESSAGE)
    @Pattern(regexp = "^(\\+)?\\d{1,13}$", message = MessageConstants.FIELD_CELLPHONE_ONLY_NUMBERS_MESSAGE)
    private final String cellphone;

    private final LocalDate birthdate;

    @NotNull(message = MessageConstants.FIELD_ROLE_NULL_MESSAGE)
    private final Role role;

    @NotBlank(message = MessageConstants.FIELD_EMAIL_NULL_MESSAGE)
    @Email(message = MessageConstants.FIELD_EMAIL_IS_NOT_EMAIL_MESSAGE)
    private final String email;

    @NotBlank(message = MessageConstants.FIELD_PASSWORD_NULL_MESSAGE)
    private final String password;
}
