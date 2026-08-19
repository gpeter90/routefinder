package com.shipping.demo.common.exception;

public class ConnectionException extends RuntimeException{
    public ConnectionException(Exception exception){
        super(exception);
    }
    public ConnectionException(String errorMessage){
        super(errorMessage);
    }
    public ConnectionException(){
        super();
    }
}
