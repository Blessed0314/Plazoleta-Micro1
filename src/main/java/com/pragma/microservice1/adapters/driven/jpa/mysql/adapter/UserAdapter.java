package com.pragma.microservice1.adapters.driven.jpa.mysql.adapter;

import com.pragma.microservice1.adapters.driven.jpa.mysql.adapter.util.UserRoleValidation;
import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.*;
import com.pragma.microservice1.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.microservice1.domain.model.User;
import com.pragma.microservice1.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class UserAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;


    private final UserRoleValidation userRoleValidation = new UserRoleValidation();

    @Override
    public void saveUser(User user) {
        userCreate(user);
    }

    @Override
    public void signUp(User user) {
        UserEntity userEntity = userCreate(user);
    }

    @Override
    public User getSmsData(String dni) {
        Optional<UserEntity> userEntity = userRepository.findByDni(dni);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userEntityMapper.toModel(userEntity.get());
    }

    private UserEntity userCreate(User user) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Optional<RoleEntity> existingRole = roleRepository.findById(user.getRole().getId());
        if (existingRole.isEmpty()) {
            throw new RoleNotFoundException();
        }
        Optional<UserEntity> existingUser = userRepository.findByDni(user.getDni().trim());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        Optional<UserEntity> existingEmail = userRepository.findByEmailIgnoreCase(user.getEmail().trim());
        if (existingEmail.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if (!userRoleValidation.canCreateUser(existingRole.get(), userRepository)) {
            throw new WrongRoleException();
        }

        UserEntity userEntity = userEntityMapper.toEntity(user);
        if(Objects.equals(existingRole.get().getName(), "EMPLOYEE")){
            userEntity.setDniBoss(userRoleValidation.getBossDni());
        }
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(userEntity);
    }
}
