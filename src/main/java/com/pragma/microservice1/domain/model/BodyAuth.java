package com.pragma.microservice1.domain.model;


public class BodyAuth {
    private final String username;
    private final String message;
    private final String jwt;
    private final boolean status;

    public BodyAuth(String username, String message, String jwt, boolean status) {
        this.username = username;
        this.message = message;
        this.jwt = jwt;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public String getJwt() {
        return jwt;
    }

    public boolean isStatus() {
        return status;
    }
}