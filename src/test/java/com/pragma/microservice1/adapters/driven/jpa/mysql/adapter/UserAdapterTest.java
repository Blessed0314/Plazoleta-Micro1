package com.pragma.microservice1.adapters.driven.jpa.mysql.adapter;

import com.pragma.microservice1.adapters.driven.jpa.mysql.adapter.util.UserRoleValidation;
import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.EmailAlreadyExistsException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.RoleNotFoundException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.UserAlreadyExistsException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.UserNotFoundException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.microservice1.adapters.security.jwt.JwtUtils;
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

public class UserAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRoleValidation userRoleValidation;

    @InjectMocks
    private UserAdapter userAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_shouldSaveUser_whenUserIsValid() {
        User user = createUser("12345678", "john.doe@example.com");
        UserEntity userEntity = createUserEntity();
        RoleEntity roleEntity = createRoleEntity();

        when(roleRepository.findById(user.getRole().getId())).thenReturn(Optional.of(roleEntity));
        when(userRepository.findByEmailIgnoreCase(user.getEmail().trim())).thenReturn(Optional.empty());
        when(userEntityMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userRoleValidation.canCreateUser(any(RoleEntity.class), any(IUserRepository.class))).thenReturn(true);

        userAdapter.saveUser(user);

        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void saveUser_shouldThrowRoleNotFoundException_whenRoleDoesNotExist() {
        User user = createUser("12345678", "john.doe@example.com");

        when(roleRepository.findById(user.getRole().getId())).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> userAdapter.saveUser(user));

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void saveUser_shouldThrowUserAlreadyExistsException_whenUserAlreadyExists() {
        User user = createUser("12345678", "john.doe1@example.com");
        RoleEntity roleEntity = createRoleEntity();
        UserEntity existingUser = createUserEntity();

        when(roleRepository.findById(user.getRole().getId())).thenReturn(Optional.of(roleEntity));
        when(userRepository.findByDni(user.getDni().trim())).thenReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class, () -> userAdapter.saveUser(user));

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void saveUser_shouldThrowEmailAlreadyExistsException_whenEmailAlreadyExists() {
        User user = createUser("123456789", "john.doe@example.com");
        RoleEntity roleEntity = createRoleEntity();
        UserEntity existingEmail = createUserEntity();

        when(roleRepository.findById(user.getRole().getId())).thenReturn(Optional.of(roleEntity));
        when(userRepository.findByEmailIgnoreCase(user.getEmail().trim())).thenReturn(Optional.of(existingEmail));

        assertThrows(EmailAlreadyExistsException.class, () -> userAdapter.saveUser(user));

        verify(userRepository, never()).save(any(UserEntity.class));
    }


    @Test
    void getRoleName_shouldReturnRoleName_whenUserExists() {
        String dni = "12345678";
        String expectedRoleName = "ADMIN";
        UserEntity userEntity = createUserEntity();

        when(userRepository.findByDni(dni)).thenReturn(Optional.of(userEntity));

        String roleName = userAdapter.getRoleName(dni);

        assertEquals(expectedRoleName, roleName);
    }

    @Test
    void getRoleName_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        String dni = "12345678";

        when(userRepository.findByDni(dni)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userAdapter.getRoleName(dni));
    }

    private User createUser(String dni, String email) {
        return new User("John", "Doe", dni, new Role(1L, "ADMIN", "Regular user"), "555-1234", LocalDate.now().minusYears(18), email, "password");
    }

    private UserEntity createUserEntity() {
        RoleEntity roleEntity = createRoleEntity();
        return new UserEntity("12345678", "John", "Doe", "555-1234", LocalDate.now().minusYears(18), "john.doe@example.com", "encodedPassword", roleEntity);
    }

    private RoleEntity createRoleEntity() {
        return new RoleEntity(1L, "ADMIN", "Regular user");
    }
}
