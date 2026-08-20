package com.routefinder.common.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BusinessLogicException extends RuntimeException {

    private List<String> messageVariableList;

    public BusinessLogicException(String errorMessage) {
        super(errorMessage);
    }

    public BusinessLogicException(String message, List<String> messageVariableList) {
        super(message);
        this.messageVariableList = messageVariableList;
    }
}
