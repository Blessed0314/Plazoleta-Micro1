package com.pragma.microservice1.domain.spi;

import com.pragma.microservice1.domain.model.Role;

public interface IRolePersistencePort {
    void createDefaultRoles(String roleName);
    void saveRol(Role role);
}
