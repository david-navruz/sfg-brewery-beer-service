package com.udemy.msscbeerservice.web.controller.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String err) {
        super(err);
    }

}