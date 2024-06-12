package com.pragma.microservice1.domain.api.usecase;

import com.pragma.microservice1.domain.api.IUserServicePort;
import com.pragma.microservice1.domain.exception.BirthdateNotNullException;
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
    public void saveOwnerUser(User user) {
        if (user.getBirthdate() == null){
            throw new BirthdateNotNullException();
        }
        if (Period.between(user.getBirthdate(), LocalDate.now()).getYears() < 18){
            throw new WrongAgeException();
        }
        userPersistencePort.saveUser(user);
    }

    @Override
    public void saveUserNotOwner(User user) {
        userPersistencePort.saveUser(user);
    }

    @Override
    public void signUp(User user) {
        userPersistencePort.signUp(user);
    }
}
