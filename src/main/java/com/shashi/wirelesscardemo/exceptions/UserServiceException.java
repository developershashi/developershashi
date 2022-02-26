package com.shashi.wirelesscardemo.exceptions;

public class UserServiceException extends RuntimeException{

    private final String message;

    public UserServiceException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "UserServiceException{" +
                "message='" + message + '\'' +
                '}';
    }
}
