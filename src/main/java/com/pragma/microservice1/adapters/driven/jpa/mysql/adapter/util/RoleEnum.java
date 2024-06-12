package com.pragma.microservice1.adapters.driven.jpa.mysql.adapter.util;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("ADMIN", "All permissions"),
    OWNER("OWNER", "Can create employee accounts, view order times and create on-site dishes."),
    EMPLOYEE("EMPLOYEE", "List pending orders, assign them, notify that they are ready and deliver them"),
    CLIENT("CLIENT","List restaurants and their respective dishes, request and cancel orders and see traceability " );

    private final String name;
    private final String description;

    RoleEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
