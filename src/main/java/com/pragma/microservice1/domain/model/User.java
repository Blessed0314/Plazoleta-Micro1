package com.pragma.microservice1.domain.model;

import java.time.LocalDate;

public class User {
    private final String name;
    private final String lastname;
    private final String dni;
    private final Role role;
    private final String cellphone;
    private final LocalDate birthdate;
    private final String email;
    private final String password;

    public User(String name, String lastname, String dni, Role role, String cellphone, LocalDate birthdate, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.role = role;
        this.cellphone = cellphone;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDni() {
        return dni;
    }

    public String getCellphone() {
        return cellphone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
