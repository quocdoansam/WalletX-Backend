package com.quocdoansam.walletx.enums;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorMessage {
    INVALID_USERNAME(HttpStatus.BAD_REQUEST, "The username must be at least 3 up to 30 characters."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "The password must be at least 6 up to 60 characters."),
    WRONG_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Wrong username or password. Try again."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "You do not have permission to access this resource."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "The user cannot be found."),
    USERNAME_EXISTED(HttpStatus.BAD_REQUEST, "The username has existed.")
    ;

    HttpStatus status;
    String message;
}
