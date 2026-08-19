package com.shipping.demo.common.exception;

public class TechnicalException extends RuntimeException {
    public TechnicalException(String errorMessage) {
        super(errorMessage);
    }

    public TechnicalException(Exception exception) {
        super(exception);
    }

    public TechnicalException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }
}
