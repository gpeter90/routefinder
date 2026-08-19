package com.shipping.demo.common.exception;

public class DomainValidationException extends RuntimeException {
    public DomainValidationException(String errorMessage) {
        super(errorMessage);
    }
}
