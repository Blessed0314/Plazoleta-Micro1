package com.pragma.microservice1.domain.api;

import com.pragma.microservice1.domain.model.BodyAuth;
import com.pragma.microservice1.domain.model.User;

public interface ILoginUserServicePort {
    BodyAuth loginUser(User user);
}
