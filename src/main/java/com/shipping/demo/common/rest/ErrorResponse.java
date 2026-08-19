package com.shipping.demo.common.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String errorMessage;
    private List<String> messageVariableList;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorResponse(String errorMessage, List<String> messageVariableList) {
        this.errorMessage = errorMessage;
        this.messageVariableList = messageVariableList;
    }
}