package com.shashi.wirelesscardemo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse<T> implements Serializable {


    private T data;
    private String responseMessage;
    private HttpStatus httpStatus;
    private int statusCode;

    public UserResponse(T data, String responseMessage, HttpStatus httpStatus, int statusCode) {
        this.data = data;
        this.responseMessage = responseMessage;
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}