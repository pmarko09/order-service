package com.pmarko09.order_service.exception.order;

public class OrderAlreadyWithInvoiceException extends RuntimeException {
    public OrderAlreadyWithInvoiceException(String message) {
        super(message);
    }
}