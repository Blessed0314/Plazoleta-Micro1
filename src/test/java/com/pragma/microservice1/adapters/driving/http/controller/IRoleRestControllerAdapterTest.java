package com.pragma.microservice1.adapters.driving.http.controller;

import com.pragma.microservice1.adapters.driving.http.dto.request.AddRoleRequest;
import com.pragma.microservice1.adapters.driving.http.mapper.IRoleRequestMapper;
import com.pragma.microservice1.domain.api.IRoleServicePort;
import com.pragma.microservice1.domain.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class IRoleRestControllerAdapterTest {

    @Mock
    private IRoleServicePort roleServicePort;

    @Mock
    private IRoleRequestMapper roleRequestMapper;

    @InjectMocks
    private IRoleRestControllerAdapter roleRestControllerAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addRole_ShouldReturnCreatedStatus() {

        Role role = new Role(1L, "ADMIN", "Administrator");
        AddRoleRequest addRoleRequest = new AddRoleRequest(role.getName(), role.getDescription());


        when(roleRequestMapper.addRequestToRole(any(AddRoleRequest.class))).thenReturn(role);
        doNothing().when(roleServicePort).saveRole(any(Role.class));


        ResponseEntity<Void> response = roleRestControllerAdapter.addRole(addRoleRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
