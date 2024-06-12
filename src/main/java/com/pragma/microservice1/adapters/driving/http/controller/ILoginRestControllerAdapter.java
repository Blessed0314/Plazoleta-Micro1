package com.pragma.microservice1.adapters.driving.http.controller;

import com.pragma.microservice1.adapters.driving.http.dto.request.LoginUserRequest;
import com.pragma.microservice1.adapters.driving.http.dto.response.LoginUserResponse;
import com.pragma.microservice1.adapters.driving.http.mapper.ILoginUserRequestMapper;
import com.pragma.microservice1.adapters.driving.http.mapper.ILoginUserResponseMapper;
import com.pragma.microservice1.domain.api.ILoginUserServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@PreAuthorize("denyAll()")
public class ILoginRestControllerAdapter {
    private final ILoginUserServicePort loginUserServicePort;
    private final ILoginUserRequestMapper loginUserRequestMapper;
    private final ILoginUserResponseMapper loginUserResponseMapper;
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<LoginUserResponse> loginUser(@Valid @RequestBody LoginUserRequest request){
        return ResponseEntity.ok(loginUserResponseMapper.toLoginUserResponse(
                loginUserServicePort.loginUser(loginUserRequestMapper.addRequestToLoginUser(request))));
    }
}