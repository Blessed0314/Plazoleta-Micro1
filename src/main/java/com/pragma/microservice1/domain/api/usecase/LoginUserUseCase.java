package com.pragma.microservice1.domain.api.usecase;

import com.pragma.microservice1.domain.api.ILoginUserServicePort;
import com.pragma.microservice1.domain.model.BodyAuth;
import com.pragma.microservice1.domain.model.User;
import com.pragma.microservice1.domain.spi.ILoginUserPersistencePort;

public class LoginUserUseCase implements ILoginUserServicePort {

    private final ILoginUserPersistencePort loginUserPersistencePort;

    public LoginUserUseCase(ILoginUserPersistencePort loginUserPersistencePort) {
        this.loginUserPersistencePort = loginUserPersistencePort;
    }
    @Override
    public BodyAuth loginUser(User user) {
        return loginUserPersistencePort.loginUser(user);
    }
}
