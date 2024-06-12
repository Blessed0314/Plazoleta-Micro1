package com.pragma.microservice1.adapters.driven.jpa.mysql.adapter;

import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.RoleAlreadyExistsException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.mapper.IRoleEntityMapper;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.microservice1.domain.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleAdapterTest {

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private IRoleEntityMapper roleEntityMapper;

    @InjectMocks
    private RoleAdapter roleAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDefaultRoles_shouldSaveRole_whenRoleDoesNotExist() {
        String roleName = "ADMIN";
        RoleEntity defaultRoleEntity = new RoleEntity(1L, roleName, "Default role description");

        when(roleRepository.findByNameIgnoreCase(roleName)).thenReturn(Optional.empty());
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(defaultRoleEntity);

        roleAdapter.createDefaultRoles(roleName);

        verify(roleRepository, times(1)).save(any(RoleEntity.class));
    }

    @Test
    void createDefaultRoles_shouldNotSaveRole_whenRoleExists() {
        String roleName = "USER";
        RoleEntity existingRoleEntity = new RoleEntity(1L, roleName, "Existing role description");

        when(roleRepository.findByNameIgnoreCase(roleName)).thenReturn(Optional.of(existingRoleEntity));

        roleAdapter.createDefaultRoles(roleName);

        verify(roleRepository, never()).save(any(RoleEntity.class));
    }

    @Test
    void saveRol_shouldSaveRole_whenRoleDoesNotExist() {
        Role role = new Role(1L, "USER", "Regular user");
        RoleEntity roleEntity = new RoleEntity(1L, role.getName(), role.getDescription());

        when(roleRepository.findByNameIgnoreCase(role.getName().trim())).thenReturn(Optional.empty());
        when(roleEntityMapper.toEntity(any(Role.class))).thenReturn(roleEntity);
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

        roleAdapter.saveRol(role);

        verify(roleRepository, times(1)).save(roleEntity);
    }

    @Test
    void saveRol_shouldThrowRoleAlreadyExistsException_whenRoleExists() {
        Role role = new Role(1L, "USER", "Regular user");
        RoleEntity existingRoleEntity = new RoleEntity(1L, role.getName(), role.getDescription());

        when(roleRepository.findByNameIgnoreCase(role.getName().trim())).thenReturn(Optional.of(existingRoleEntity));

        assertThrows(RoleAlreadyExistsException.class, () -> roleAdapter.saveRol(role));

        verify(roleRepository, never()).save(any(RoleEntity.class));
    }
}
