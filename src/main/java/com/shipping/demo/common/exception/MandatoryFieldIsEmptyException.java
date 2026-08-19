package com.shipping.demo.common.exception;

public class MandatoryFieldIsEmptyException extends RuntimeException {
    public MandatoryFieldIsEmptyException(String errorMessage) {
        super(errorMessage);
    }
}
