package com.shipping.demo.common.rest;

import com.shipping.demo.common.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BusinessLogicException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public final ResponseEntity<ErrorResponse> handleBusinessLogicException(BusinessLogicException exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorResponse(
                        exception.getMessage(),
                        exception.getMessageVariableList() != null ? exception.getMessageVariableList() : null
                        )
                );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorResponse> handleInvalidParameterException(InvalidParameterException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(ConnectionException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public final ResponseEntity<ErrorResponse> handleConnectionException() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse("Service unavailable!"));
    }

    @ExceptionHandler(TechnicalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ErrorResponse> handleTechnicalException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse("Internal server error occurred!"));
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull  WebRequest request
    ) {
        String errorMessage =
                StringUtils.isNotEmpty(exception.getMessage())
                        ?
                        trunkErrorMessage(exception.getMessage())
                        :
                        "Bad request!";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(errorMessage));
    }

    private String trunkErrorMessage(String errorMessage) {
        errorMessage = removeNestedExceptionMessagePart(errorMessage);
        errorMessage = removeDetailsMessagePart(errorMessage);
        errorMessage = removeJavaClassesMessagePart(errorMessage);
        errorMessage = removeApostrophes(errorMessage);
        errorMessage = removeWhitespaces(errorMessage);
        return errorMessage;
    }

    private String removeNestedExceptionMessagePart(String errorMessage) {
        int startCharacterIndexForSubString = errorMessage.indexOf(";");
        if (startCharacterIndexForSubString > 0) {
            errorMessage = errorMessage.substring(0, startCharacterIndexForSubString);
        }
        return errorMessage;
    }

    private String removeDetailsMessagePart(String errorMessage) {
        int startCharacterIndexForSubString = errorMessage.indexOf("(");
        if (startCharacterIndexForSubString > 0) {
            errorMessage = errorMessage.substring(0, startCharacterIndexForSubString);
        }
        return errorMessage;
    }

    private String removeJavaClassesMessagePart(String errorMessage) {
        return errorMessage.replaceAll("of type `java.(.*)`", "");
    }

    private String removeApostrophes(String errorMessage) {
        return errorMessage.replaceAll("\"", "'");
    }

    private String removeWhitespaces(String errorMessage) {
        return errorMessage.replaceAll(" +", " ").trim();
    }
}
