package com.jkj.benefits_tracker.exception;

public class InvalidPostcodeException extends RuntimeException{
    public InvalidPostcodeException(String message){
        super(message);
    }
}
