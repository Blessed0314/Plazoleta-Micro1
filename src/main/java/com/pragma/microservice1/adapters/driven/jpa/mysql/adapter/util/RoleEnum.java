package com.pragma.microservice1.adapters.driven.jpa.mysql.adapter.util;

import lombok.Getter;

@Getter
public enum RoleEnum {
    SUPER_ADMIN("ADMIN", "All permissions"),
    ADMIN("OWNER", "Can create employee accounts, view order times and create on-site dishes."),
    TUTOR("EMPLOYEE", "List pending orders, assign them, notify that they are ready and deliver them"),
    STUDENT("CLIENT", "View restaurants and dishes, order and cancel order");

    private final String name;
    private final String description;

    RoleEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
