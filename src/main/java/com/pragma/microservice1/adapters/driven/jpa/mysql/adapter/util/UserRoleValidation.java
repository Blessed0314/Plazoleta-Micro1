package com.pragma.microservice1.adapters.driven.jpa.mysql.adapter.util;

import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.microservice1.adapters.driven.jpa.mysql.repository.IUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserRoleValidation {

    public boolean canCreateUser (RoleEntity role, IUserRepository userRepository) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return switch (role.getName()) {
            case "ADMIN" -> !userRepository.existsByRole(role);
            case "OWNER" -> getAuthority(authentication).equals("ADMIN");
            case "EMPLOYEE" -> getAuthority(authentication).equals("OWNER");
            case "CLIENT" -> true;
            default -> false;
        };
    }

    public String getBossDni () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserEntity userDetails) {
            return userDetails.getDni();
        }
        return null;
    }

    private String getAuthority (Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().startsWith("ROLE_")) {
                    return authority.getAuthority().substring(5);
                }
            }
        }
        return null;
    }
}
