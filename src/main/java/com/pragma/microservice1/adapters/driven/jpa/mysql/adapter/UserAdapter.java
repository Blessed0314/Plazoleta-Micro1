package com.pragma.microservice1.adapters.driven.jpa.mysql.adapter;

import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.EmailAlreadyExistsException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.RoleNotFoundException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.UserAlreadyExistsException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.microservice1.domain.model.User;
import com.pragma.microservice1.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
public class UserAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;

    @Override
    public void saveUser(User user) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Optional<RoleEntity> existingRole = Optional.ofNullable(roleRepository.findByNameIgnoreCase(user.getRole().getName())
                .orElseThrow(RoleNotFoundException::new));
        Optional<UserEntity> existingUser = userRepository.findByEmailIgnoreCase(user.getEmail().trim());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        Optional<UserEntity> existingEmail = userRepository.findByEmailIgnoreCase(user.getEmail().trim());
        if (existingEmail.isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        UserEntity userEntity = userEntityMapper.toEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);
    }
}
