package com.pmarko09.order_service.exception.cart;

public class CartServiceUnavailableException extends RuntimeException {
    public CartServiceUnavailableException(String message) {
        super(message);
    }
}
