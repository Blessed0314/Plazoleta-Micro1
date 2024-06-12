package com.pragma.microservice1.adapters.driving.http.controller;

import com.pragma.microservice1.adapters.driving.http.dto.request.AddUserRequest;
import com.pragma.microservice1.adapters.driving.http.mapper.IUserRequestMapper;
import com.pragma.microservice1.domain.api.IUserServicePort;
import com.pragma.microservice1.domain.model.Role;
import com.pragma.microservice1.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class IUserRestControllerAdapterTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @InjectMocks
    private IUserRestControllerAdapter userRestControllerAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private AddUserRequest createAddUserRequest(String dni, String name, String lastname, String cellphone, LocalDate birthdate, Long roleId, String roleName, String roleDescription, String email, String password) {
        Role role = new Role(roleId, roleName, roleDescription);
        return new AddUserRequest(dni, name, lastname, cellphone, birthdate, role, email, password);
    }

    private User createUser(String dni, String name, String lastname, String cellphone, LocalDate birthdate, Long roleId, String roleName, String roleDescription, String email, String password) {
        Role role = new Role(roleId, roleName, roleDescription);
        return new User(name, lastname, dni, role, cellphone, birthdate, email, password);
    }

    @Test
    public void addOwnerUser_ShouldReturnCreatedStatus() {
        AddUserRequest addUserRequest = createAddUserRequest("123456789", "John", "Doe", "1234567890", LocalDate.of(1990, 1, 1), 1L, "OWNER", "Owner Role", "john.doe@example.com", "password");
        User user = createUser("123456789", "John", "Doe", "1234567890", LocalDate.of(1990, 1, 1), 1L, "OWNER", "Owner Role", "john.doe@example.com", "password");

        when(userRequestMapper.addRequestToUser(any(AddUserRequest.class))).thenReturn(user);
        doNothing().when(userServicePort).saveOwnerUser(any(User.class));

        ResponseEntity<Void> response = userRestControllerAdapter.addOwnerUser(addUserRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addEmployeeUser_ShouldReturnCreatedStatus() {
        AddUserRequest addUserRequest = createAddUserRequest("987654321", "Jane", "Doe", "0987654321", LocalDate.of(1992, 2, 2), 2L, "EMPLOYEE", "Employee Role", "jane.doe@example.com", "password");
        User user = createUser("987654321", "Jane", "Doe", "0987654321", LocalDate.of(1992, 2, 2), 2L, "EMPLOYEE", "Employee Role", "jane.doe@example.com", "password");

        when(userRequestMapper.addRequestToUser(any(AddUserRequest.class))).thenReturn(user);
        doNothing().when(userServicePort).saveUserNotOwner(any(User.class));

        ResponseEntity<Void> response = userRestControllerAdapter.addEmployeeUser(addUserRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void signUpUser_ShouldReturnCreatedStatus() {
        AddUserRequest addUserRequest = createAddUserRequest("123123123", "Sam", "Smith", "1231231234", LocalDate.of(2000, 3, 3), 3L, "USER", "User Role", "sam.smith@example.com", "password");
        User user = createUser("123123123", "Sam", "Smith", "1231231234", LocalDate.of(2000, 3, 3), 3L, "USER", "User Role", "sam.smith@example.com", "password");

        when(userRequestMapper.addRequestToUser(any(AddUserRequest.class))).thenReturn(user);
        doNothing().when(userServicePort).signUp(any(User.class));

        ResponseEntity<Void> response = userRestControllerAdapter.signUpUser(addUserRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
