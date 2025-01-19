package com.pmarko09.order_service.exception.order;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super(String.format("Order with id: %s not found", id));
    }
}
