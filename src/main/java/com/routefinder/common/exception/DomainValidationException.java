package com.routefinder.common.exception;

public class DomainValidationException extends RuntimeException {
    public DomainValidationException(String errorMessage) {
        super(errorMessage);
    }
}
