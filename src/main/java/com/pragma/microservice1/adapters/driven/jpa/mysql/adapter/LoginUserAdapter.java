package com.pragma.microservice1.adapters.driven.jpa.mysql.adapter;

import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.UserNotFoundException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.WrongPasswordException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.microservice1.adapters.security.jwt.JwtUtils;
import com.pragma.microservice1.domain.model.BodyAuth;
import com.pragma.microservice1.domain.model.User;
import com.pragma.microservice1.domain.spi.ILoginUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class LoginUserAdapter implements ILoginUserPersistencePort {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;

    @Override
    public BodyAuth loginUser(User user) {

        Authentication authentication = authenticate(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity entity = userRepository.findByEmailIgnoreCase(user.getEmail()).orElseThrow(UserNotFoundException::new);
        String accessToken = jwtUtils.createToken(authentication, entity.getDni());

        return new BodyAuth(user.getEmail(), "User successfully logged in", accessToken, true);
    }

    private Authentication authenticate (String username, String password){
        UserDetails userDetails = userRepository.findByEmailIgnoreCase(username).orElseThrow(UserNotFoundException::new);
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new WrongPasswordException();
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
