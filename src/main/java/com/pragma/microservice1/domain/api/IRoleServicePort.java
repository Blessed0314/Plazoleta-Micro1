package com.pragma.microservice1.domain.api;

import com.pragma.microservice1.domain.model.Role;

public interface IRoleServicePort {
    void createDefaultRoles(String roleName);
    void saveRole(Role role);
}
