package com.shashi.wirelesscardemo.enums;

import org.springframework.http.HttpStatus;

public enum CommonState {

    USER_CREATED("user_created","user created successfully!!", HttpStatus.CREATED),
    USER_BAD_REQUEST("user_data_not_found", "user data not found in request",HttpStatus.BAD_REQUEST),
    USER_DELETED("user_deleted", "user deleted successfully!!",HttpStatus.NO_CONTENT);
    private String code;
    private String message;
    private HttpStatus errorStatus;

    CommonState(String code, String message, HttpStatus errorStatus) {
        this.code = code;
        this.message = message;
        this.errorStatus = errorStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }
}
