package com.shipping.demo.common.exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String errorMessage) {
        super(errorMessage);
    }
}
