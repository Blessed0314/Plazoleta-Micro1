package com.pragma.microservice1.domain.spi;

import com.pragma.microservice1.domain.model.User;

public interface IUserPersistencePort {
    void saveUser(User user);
    void saveAdmin(User user);
}
