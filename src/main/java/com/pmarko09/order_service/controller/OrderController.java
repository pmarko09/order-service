package com.pmarko09.order_service.controller;

import com.pmarko09.order_service.model.dto.InvoiceDto;
import com.pmarko09.order_service.model.dto.OrderDto;
import com.pmarko09.order_service.model.dto.OrderRequestDto;
import com.pmarko09.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDto processOrder(@RequestBody OrderRequestDto orderRequest) {
        return orderService.processOrder(orderRequest);
    }

    @GetMapping
    public List<OrderDto> getOrdersByClientId(@RequestParam Long clientId) {
        return orderService.getOrdersByClient(clientId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/invoice/{orderId}")
    public InvoiceDto generateInvoice(@PathVariable Long orderId) {
        return orderService.generateInvoice(orderId);
    }

    @GetMapping("/invoice/{orderId}")
    public InvoiceDto getInvoiceByOrderId(@PathVariable Long orderId) {
        return orderService.getInvoiceByOrderId(orderId);
    }
}