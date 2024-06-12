package com.pragma.microservice1.domain.api.usecase;

import com.pragma.microservice1.domain.exception.BirthdateNotNullException;
import com.pragma.microservice1.domain.exception.WrongAgeException;
import com.pragma.microservice1.domain.model.Role;
import com.pragma.microservice1.domain.model.User;
import com.pragma.microservice1.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOwnerUser_shouldThrowBirthdateNotNullException_whenBirthdateIsNull() {
        User user = createUser(null);

        assertThrows(BirthdateNotNullException.class, () -> {
            userUseCase.saveOwnerUser(user);
        });
    }

    @Test
    void saveOwnerUser_shouldThrowWrongAgeException_whenAgeIsLessThan18() {
        User user = createUser(17);

        assertThrows(WrongAgeException.class, () -> {
            userUseCase.saveOwnerUser(user);
        });
    }

    @Test
    void saveOwnerUser_shouldCallSaveUser_whenAgeIsGreaterThanOrEqual18() {
        User user = createUser(18);

        userUseCase.saveOwnerUser(user);

        verify(userPersistencePort).saveUser(user);
    }

    @Test
    void saveUserNotOwner_shouldCallSaveUser() {
        User user = createUser(25);

        userUseCase.saveUserNotOwner(user);

        verify(userPersistencePort).saveUser(user);
    }

    @Test
    void signUp_shouldCallSignUp() {
        User user = createUser(30);

        userUseCase.signUp(user);

        verify(userPersistencePort).signUp(user);
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
