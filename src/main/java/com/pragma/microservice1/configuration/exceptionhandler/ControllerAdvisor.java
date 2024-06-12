package com.pragma.microservice1.configuration.exceptionhandler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.pragma.microservice1.adapters.driven.jpa.mysql.exception.*;
import com.pragma.microservice1.adapters.security.exception.JwtInvalidException;
import com.pragma.microservice1.configuration.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(BindException bindException) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError error : bindException.getFieldErrors()) {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        }
        errorMessage.delete(errorMessage.length() - 2, errorMessage.length());
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                errorMessage.toString(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(
                Constants.ROLE_PERMISSIONS_EXCEPTION_MESSAGE,
                HttpStatus.FORBIDDEN.toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleEmailAlreadyExistsException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                Constants.EMAIL_ALREADY_EXISTS_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleRoleAlreadyExistsException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                Constants.ROLE_ALREADY_EXISTS_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                Constants.USER_ALREADY_EXISTS_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                Constants.USER_NOT_FOUND_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ExceptionResponse> handleWrongPasswordException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                Constants.WRONG_PASSWORD_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }


    @ExceptionHandler(WrongRoleException.class)
    public ResponseEntity<ExceptionResponse> handleWrongRoleException() {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                Constants.WRONG_ROLE_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRoleNotFoundException(RoleNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                Constants.ROLE_NOT_FOUND_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleJwtInvalidException(){
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                Constants.JWT_VERIFICATION_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(JwtInvalidException.class)
    public ResponseEntity<ExceptionResponse> handleJwtInvalidException(JwtInvalidException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                Constants.JWT_VERIFICATION_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        ));
    }
}
