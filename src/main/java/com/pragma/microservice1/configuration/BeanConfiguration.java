package com.pragma.microservice1.configuration;

import com.pragma.microservice1.adapters.driven.jpa.mysql.adapter.LoginUserAdapter;
import com.pragma.microservice1.adapters.driven.jpa.mysql.adapter.RoleAdapter;
import com.pragma.microservice1.adapters.driven.jpa.mysql.adapter.UserAdapter;
import com.pragma.microservice1.adapters.driven.jpa.mysql.mapper.IRoleEntityMapper;
import com.pragma.microservice1.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.microservice1.adapters.security.jwt.JwtUtils;
import com.pragma.microservice1.domain.api.ILoginUserServicePort;
import com.pragma.microservice1.domain.api.IRoleServicePort;
import com.pragma.microservice1.domain.api.IUserServicePort;
import com.pragma.microservice1.domain.api.usecase.LoginUserUseCase;
import com.pragma.microservice1.domain.api.usecase.RoleUseCase;
import com.pragma.microservice1.domain.api.usecase.UserUseCase;
import com.pragma.microservice1.domain.spi.ILoginUserPersistencePort;
import com.pragma.microservice1.domain.spi.IRolePersistencePort;
import com.pragma.microservice1.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IRoleServicePort roleServicePort() {
        return new RoleUseCase(rolePersistencePort());
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserAdapter(userRepository, userEntityMapper, roleRepository);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }

    @Bean
    public ILoginUserPersistencePort loginUserPersistencePort() {
        return new LoginUserAdapter(jwtUtils, passwordEncoder, userRepository);
    }

    @Bean
    public ILoginUserServicePort loginUserServicePort() {
        return new LoginUserUseCase(loginUserPersistencePort());
    }
}

