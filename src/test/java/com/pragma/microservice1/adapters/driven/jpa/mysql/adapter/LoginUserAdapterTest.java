package com.pragma.microservice1.adapters.driven.jpa.mysql.adapter;

import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.UserNotFoundException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.WrongPasswordException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.microservice1.adapters.security.jwt.JwtUtils;
import com.pragma.microservice1.domain.model.BodyAuth;
import com.pragma.microservice1.domain.model.Role;
import com.pragma.microservice1.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginUserAdapterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private LoginUserAdapter loginUserAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginUser_shouldReturnBodyAuthWithToken_whenCredentialsAreCorrect() {
        User user = createUser();
        UserEntity userEntity = createUserEntity();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword(), userEntity.getAuthorities());

        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(user.getPassword(), userEntity.getPassword())).thenReturn(true);
        when(jwtUtils.createToken(authentication, user.getDni())).thenReturn("token123");

        BodyAuth bodyAuth = loginUserAdapter.loginUser(user);

        assertNotNull(bodyAuth);
        assertEquals(user.getEmail(), bodyAuth.getUsername());
        assertEquals("User successfully logged in", bodyAuth.getMessage());
        assertEquals("token123", bodyAuth.getJwt());
        assertTrue(bodyAuth.isStatus());
    }

    @Test
    void loginUser_shouldThrowUserNotFoundException_whenUserIsNotFound() {
        User user = createUser();

        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> loginUserAdapter.loginUser(user));
    }

    @Test
    void loginUser_shouldThrowWrongPasswordException_whenPasswordIsIncorrect() {
        User user = createUser();
        UserEntity userEntity = createUserEntity();

        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(user.getPassword(), userEntity.getPassword())).thenReturn(false);

        assertThrows(WrongPasswordException.class, () -> loginUserAdapter.loginUser(user));
    }

    private User createUser() {
        String name = "John";
        String lastname = "Doe";
        String dni = "12345678";
        Role role = new Role(1L, "USER", "Regular user");
        String cellphone = "555-1234";
        LocalDate birthdate = LocalDate.now().minusYears(18);
        String email = "john.doe@example.com";
        String password = "password";

        return new User(name, lastname, dni, role, cellphone, birthdate, email, password);
    }

    private UserEntity createUserEntity() {
        String dni = "12345678";
        String name = "John";
        String lastname = "Doe";
        String cellphone = "555-1234";
        LocalDate birthdate = LocalDate.now().minusYears(18);
        String email = "john.doe@example.com";
        String password = "encodedPassword";
        RoleEntity role = new RoleEntity(1L, "USER", "Regular user");

        return new UserEntity(dni, name, lastname, cellphone, birthdate, email, password, role);
    }
}
