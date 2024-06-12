package com.pragma.microservice1.domain.api;

import com.pragma.microservice1.domain.model.User;

public interface IUserServicePort {
    void saveOwnerUser(User user);
    void saveUserNotOwner(User user);
    void signUp(User user);
}
