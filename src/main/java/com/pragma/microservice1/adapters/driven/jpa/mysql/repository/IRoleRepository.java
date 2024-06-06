package com.pragma.microservice1.adapters.driven.jpa.mysql.repository;

import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByNameIgnoreCase(String name);
    RoleEntity findByName(String name);
}
