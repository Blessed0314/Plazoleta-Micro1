package com.pragma.microservice1.domain.api.usecase;

import com.pragma.microservice1.domain.api.IUserServicePort;
import com.pragma.microservice1.domain.exception.WrongAgeException;
import com.pragma.microservice1.domain.model.User;
import com.pragma.microservice1.domain.spi.IUserPersistencePort;

import java.time.LocalDate;
import java.time.Period;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveUser(User user) {
        int age = Period.between(user.getBirthdate(), LocalDate.now()).getYears();
        if (age < 18){
            throw new WrongAgeException();
        }
        userPersistencePort.saveUser(user);
    }
}
