package com.pragma.microservice1.adapters.driving.http.controller;

import com.pragma.microservice1.adapters.driving.http.dto.request.LoginUserRequest;
import com.pragma.microservice1.adapters.driving.http.dto.response.LoginUserResponse;
import com.pragma.microservice1.adapters.driving.http.mapper.ILoginUserRequestMapper;
import com.pragma.microservice1.adapters.driving.http.mapper.ILoginUserResponseMapper;
import com.pragma.microservice1.domain.api.ILoginUserServicePort;
import com.pragma.microservice1.domain.model.BodyAuth;
import com.pragma.microservice1.domain.model.Role;
import com.pragma.microservice1.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ILoginRestControllerAdapterTest {

    @Mock
    private ILoginUserServicePort loginUserServicePort;

    @Mock
    private ILoginUserRequestMapper loginUserRequestMapper;

    @Mock
    private ILoginUserResponseMapper loginUserResponseMapper;

    @InjectMocks
    private ILoginRestControllerAdapter loginRestControllerAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loginUser_ShouldReturnLoginUserResponse() {

        User user = new User("John", "Doe", "123456789", new Role(1L, "ADMIN", "description role"), "1234567890", LocalDate.of(1990, 1, 1), "john.doe@example.com", "password");
        LoginUserRequest loginUserRequest = new LoginUserRequest(user.getEmail(), user.getPassword());
        BodyAuth bodyAuth = new BodyAuth("john.doe@example.com", "Success", "jwt-token", true);
        LoginUserResponse loginUserResponse = new LoginUserResponse("john.doe@example.com", "Success", "jwt-token", true);

        when(loginUserRequestMapper.addRequestToLoginUser(any(LoginUserRequest.class))).thenReturn(user);
        when(loginUserServicePort.loginUser(any(User.class))).thenReturn(bodyAuth);
        when(loginUserResponseMapper.toLoginUserResponse(any(BodyAuth.class))).thenReturn(loginUserResponse);

        ResponseEntity<LoginUserResponse> response = loginRestControllerAdapter.loginUser(loginUserRequest);

        assertEquals(loginUserResponse, response.getBody());
    }
}
