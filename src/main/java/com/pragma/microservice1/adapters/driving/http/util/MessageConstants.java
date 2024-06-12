package com.pragma.microservice1.adapters.driving.http.util;

public class MessageConstants {
    private MessageConstants() {
        throw new IllegalStateException("Utility class");
    }
    public static final String FIELD_DNI_NULL_MESSAGE = "Field 'DNI' cannot be null";
    public static final String FIELD_DNI_ONLY_NUMBERS_MESSAGE = "Field 'DNI' can only have numbers";
    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_LAST_NAME_NULL_MESSAGE = "Field 'last name' cannot be null";
    public static final String FIELD_CELLPHONE_NULL_MESSAGE = "Field 'cellphone' cannot be null";
    public static final String FIELD_CELLPHONE_ONLY_NUMBERS_MESSAGE = "Field 'Cellphone' and 'cellphone' can only have numbers";
    public static final String FIELD_ROLE_NULL_MESSAGE = "Field 'role' cannot be null";
    public static final String FIELD_EMAIL_NULL_MESSAGE = "Field 'email' cannot be null";
    public static final String FIELD_EMAIL_IS_NOT_EMAIL_MESSAGE = "Field 'email' is not in email format";
    public static final String FIELD_PASSWORD_NULL_MESSAGE = "Field 'password' cannot be null";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description' cannot be null";
}
