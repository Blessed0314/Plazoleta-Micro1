package com.pragma.microservice1.domain.api.usecase;

import com.pragma.microservice1.domain.model.Role;
import com.pragma.microservice1.domain.spi.IRolePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class RoleUseCaseTest {

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @InjectMocks
    private RoleUseCase roleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDefaultRoles_shouldCallCreateDefaultRoles() {
        String roleName = "ADMIN";

        roleUseCase.createDefaultRoles(roleName);

        verify(rolePersistencePort).createDefaultRoles(roleName);
    }

    @Test
    void saveRole_shouldCallSaveRole() {
        Role role = createRole(1L, "USER", "Standard user role");

        roleUseCase.saveRole(role);

        verify(rolePersistencePort).saveRol(role);
    }


    private Role createRole(Long id, String name, String description) {
        return new Role(id, name, description);
    }
}
