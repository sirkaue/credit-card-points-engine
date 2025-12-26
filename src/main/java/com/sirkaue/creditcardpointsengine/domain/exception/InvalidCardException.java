package com.sirkaue.creditcardpointsengine.domain.exception;

public class InvalidCardException extends RuntimeException {
    public InvalidCardException(String message) {
        super(message);
    }
}
