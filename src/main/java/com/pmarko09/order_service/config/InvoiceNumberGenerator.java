package com.pmarko09.order_service.config;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InvoiceNumberGenerator {

    public String generate() {
        return "FV/" + LocalDateTime.now().getYear() + "/"
                + System.currentTimeMillis() % 10000;
    }
}