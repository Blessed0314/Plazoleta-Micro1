package com.pragma.microservice1.domain.api.usecase;

import com.pragma.microservice1.domain.model.BodyAuth;
import com.pragma.microservice1.domain.model.Role;
import com.pragma.microservice1.domain.model.User;
import com.pragma.microservice1.domain.spi.ILoginUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginUserUseCaseTest {

    @Mock
    private ILoginUserPersistencePort loginUserPersistencePort;

    @InjectMocks
    private LoginUserUseCase loginUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginUser_shouldReturnBodyAuth() {
        User user = createUser(18);
        BodyAuth expectedBodyAuth = new BodyAuth(user.getEmail(), "User logged in successfully", "jwt", true);

        when(loginUserPersistencePort.loginUser(user)).thenReturn(expectedBodyAuth);

        BodyAuth resultBodyAuth = loginUserUseCase.loginUser(user);

        assertEquals(expectedBodyAuth, resultBodyAuth);
    }

    @Test
    void loginUser_shouldCallLoginUserPersistencePort() {
        User user = createUser(18);

        loginUserUseCase.loginUser(user);

        verify(loginUserPersistencePort).loginUser(user);
    }

    private User createUser(Integer age) {
        String name = "John";
        String lastname = "Doe";
        String dni = "12345678";
        Role role = new Role(1L,"proof1", "proof description");
        String cellphone = "555-1234";
        LocalDate birthdate = (age != null) ? LocalDate.now().minusYears(age) : null;
        String email = "john.doe@example.com";
        String password = "password";

        return new User(name, lastname, dni, role, cellphone, birthdate, email, password);
    }
}
