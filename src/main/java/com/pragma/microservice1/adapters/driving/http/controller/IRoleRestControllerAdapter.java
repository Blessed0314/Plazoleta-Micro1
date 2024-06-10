package com.pragma.microservice1.adapters.driving.http.controller;

import com.pragma.microservice1.adapters.driving.http.dto.request.AddRoleRequest;
import com.pragma.microservice1.adapters.driving.http.mapper.IRoleRequestMapper;
import com.pragma.microservice1.domain.api.IRoleServicePort;
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
@RequestMapping("/role")
@RequiredArgsConstructor
@PreAuthorize("denyAll()")
public class IRoleRestControllerAdapter {
    private final IRoleServicePort roleServicePort;
    private final IRoleRequestMapper roleRequestMapper;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addRole(@Valid @RequestBody AddRoleRequest request){
        roleServicePort.saveRole(roleRequestMapper.addRequestToRole(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}