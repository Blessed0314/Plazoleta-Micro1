package com.pragma.microservice1.domain.spi;

import com.pragma.microservice1.domain.model.BodyAuth;
import com.pragma.microservice1.domain.model.User;

public interface ILoginUserPersistencePort {
    BodyAuth loginUser(User user);
}
