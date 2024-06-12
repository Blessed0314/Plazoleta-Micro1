package com.pragma.microservice1.adapters.driven.jpa.mysql.repository;

import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, String>{
    Optional<UserEntity> findByDni(String dni);
    Optional<UserEntity> findByEmailIgnoreCase(String email);
    boolean existsByRole(RoleEntity role);
}
