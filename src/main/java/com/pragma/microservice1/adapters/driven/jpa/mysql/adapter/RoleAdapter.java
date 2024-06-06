package com.pragma.microservice1.adapters.driven.jpa.mysql.adapter;

import com.pragma.microservice1.adapters.driven.jpa.mysql.adapter.util.RoleConstructor;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.RoleAlreadyExistsException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.mapper.IRoleEntityMapper;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.microservice1.domain.api.IRoleServicePort;
import com.pragma.microservice1.domain.model.Role;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleAdapter implements IRoleServicePort {
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    private final RoleConstructor roleConstructor= new RoleConstructor();

    @Override
    public void createDefaultRoles(String roleName) {
        if(!isExists(roleName)){
            roleRepository.save(roleConstructor.defaultRoleBuilder(roleName));
        }
    }

    @Override
    public void saveRol(Role role) {
        if (isExists(role.getName().trim())) {
            throw new RoleAlreadyExistsException();
        }
        roleRepository.save(roleEntityMapper.toEntity(roleConstructor.roleBuilder(role)));
    }

    private boolean isExists(String roleName) {
        return roleRepository.findByNameIgnoreCase(roleName).isPresent();
    }
}
