package com.pragma.microservice1.domain.api.usecase;

import com.pragma.microservice1.domain.api.IRoleServicePort;
import com.pragma.microservice1.domain.model.Role;
import com.pragma.microservice1.domain.spi.IRolePersistencePort;

public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void createDefaultRoles(String roleName) {
        rolePersistencePort.createDefaultRoles(roleName);
    }

    @Override
    public void saveRole(Role role) {
        rolePersistencePort.saveRol(role);
    }
}
