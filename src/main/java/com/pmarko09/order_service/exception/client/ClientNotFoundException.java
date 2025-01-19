package com.pmarko09.order_service.exception.client;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super(String.format("Client with id: %s not found.", id));
    }
}