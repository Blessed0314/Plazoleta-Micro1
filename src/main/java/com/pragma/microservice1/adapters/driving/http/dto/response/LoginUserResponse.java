package com.pragma.microservice1.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginUserResponse {
    private final String username;
    private final String message;
    private final String jwt;
    private final boolean status;
}
