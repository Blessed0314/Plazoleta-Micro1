package com.pragma.microservice1.domain.api;

import com.pragma.microservice1.domain.model.User;

public interface IUserServicePort {
    void saveUser(User user);
    void saveAdmin(User user);
}
