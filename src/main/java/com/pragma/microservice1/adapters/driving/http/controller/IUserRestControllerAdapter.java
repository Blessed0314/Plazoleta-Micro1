package com.pragma.microservice1.adapters.driving.http.controller;


import com.pragma.microservice1.adapters.driving.http.dto.request.AddUserRequest;
import com.pragma.microservice1.adapters.driving.http.mapper.IUserRequestMapper;
import com.pragma.microservice1.domain.api.IUserServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("denyAll()")
public class IUserRestControllerAdapter {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    @PostMapping("/createOwner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addOwnerUser(@Valid @RequestBody AddUserRequest request){
        userServicePort.saveOwnerUser(userRequestMapper.addRequestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/createEmployee")git
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> addEmployeeUser(@Valid @RequestBody AddUserRequest request){
        userServicePort.saveUserNotOwner(userRequestMapper.addRequestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> signUpUser(@Valid @RequestBody AddUserRequest request){
        userServicePort.signUp(userRequestMapper.addRequestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
