package com.pmarko09.order_service.exception.client;

public class IllegalClientNameException extends RuntimeException {
    public IllegalClientNameException(String message) {
        super(message);
    }
}
