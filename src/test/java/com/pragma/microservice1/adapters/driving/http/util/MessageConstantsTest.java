package com.pragma.microservice1.adapters.driving.http.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageConstantsTest {

    @Test
    void testPrivateConstructor() {
        // Arrange
        String expectedMessage = "Utility class";

        // Act & Assert
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, MessageConstants::new);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testFieldDniNullMessage() {
        String expectedMessage = "Field 'DNI' cannot be null";
        assertEquals(expectedMessage, MessageConstants.FIELD_DNI_NULL_MESSAGE);
    }

    @Test
    void testFieldDniOnlyNumbersMessage() {
        String expectedMessage = "Field 'DNI' can only have numbers";
        assertEquals(expectedMessage, MessageConstants.FIELD_DNI_ONLY_NUMBERS_MESSAGE);
    }

    @Test
    void testFieldNameNullMessage() {
        String expectedMessage = "Field 'name' cannot be null";
        assertEquals(expectedMessage, MessageConstants.FIELD_NAME_NULL_MESSAGE);
    }

    @Test
    void testFieldLastNameNullMessage() {
        String expectedMessage = "Field 'last name' cannot be null";
        assertEquals(expectedMessage, MessageConstants.FIELD_LAST_NAME_NULL_MESSAGE);
    }

    @Test
    void testFieldCellphoneNullMessage() {
        String expectedMessage = "Field 'cellphone' cannot be null";
        assertEquals(expectedMessage, MessageConstants.FIELD_CELLPHONE_NULL_MESSAGE);
    }

    @Test
    void testFieldCellphoneOnlyNumbersMessage() {
        String expectedMessage = "Field 'Cellphone' and 'cellphone' can only have numbers";
        assertEquals(expectedMessage, MessageConstants.FIELD_CELLPHONE_ONLY_NUMBERS_MESSAGE);
    }

    @Test
    void testFieldRoleNullMessage() {
        String expectedMessage = "Field 'role' cannot be null";
        assertEquals(expectedMessage, MessageConstants.FIELD_ROLE_NULL_MESSAGE);
    }

    @Test
    void testFieldEmailNullMessage() {
        String expectedMessage = "Field 'email' cannot be null";
        assertEquals(expectedMessage, MessageConstants.FIELD_EMAIL_NULL_MESSAGE);
    }

    @Test
    void testFieldEmailIsNotEmailMessage() {
        String expectedMessage = "Field 'email' is not in email format";
        assertEquals(expectedMessage, MessageConstants.FIELD_EMAIL_IS_NOT_EMAIL_MESSAGE);
    }

    @Test
    void testFieldPasswordNullMessage() {
        String expectedMessage = "Field 'password' cannot be null";
        assertEquals(expectedMessage, MessageConstants.FIELD_PASSWORD_NULL_MESSAGE);
    }

    @Test
    void testFieldDescriptionNullMessage() {
        String expectedMessage = "Field 'description' cannot be null";
        assertEquals(expectedMessage, MessageConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }
}