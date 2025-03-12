package com.jkj.benefits_tracker.exception;

public class InvalidTelephoneNumberException extends RuntimeException{
    public InvalidTelephoneNumberException(String message){
        super(message);
    }
}
