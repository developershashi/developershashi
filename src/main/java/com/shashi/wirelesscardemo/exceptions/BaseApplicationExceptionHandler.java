package com.shashi.wirelesscardemo.exceptions;

import com.shashi.wirelesscardemo.models.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class BaseApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(BaseApplicationExceptionHandler.class);
    private static final String logResponse = "Application exception: {}";

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<Object> userServiceException(UserServiceException ex, WebRequest request) {
        UserResponse userResponse =new UserResponse(new ArrayList<>(), ex.getMessage(), HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity(userResponse, HttpStatus.BAD_REQUEST);
    }
}