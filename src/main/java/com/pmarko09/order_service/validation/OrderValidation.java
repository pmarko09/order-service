package com.pmarko09.order_service.validation;

import com.pmarko09.order_service.exception.cart.EmptyCartException;
import com.pmarko09.order_service.exception.order.OrderAlreadyWithInvoiceException;
import com.pmarko09.order_service.exception.order.OrderNotFoundException;
import com.pmarko09.order_service.model.dto.CartInfoDto;
import com.pmarko09.order_service.model.entity.Order;
import com.pmarko09.order_service.repository.InvoiceRepository;
import com.pmarko09.order_service.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderValidation {

    public static Order orderExists(OrderRepository orderRepository, Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public static void orderWithInvoiceCheck(InvoiceRepository invoiceRepository, Long orderId) {
        if (invoiceRepository.findByOrderId(orderId).isPresent()) {
            throw new OrderAlreadyWithInvoiceException("Invoice for order: " + orderId + " already exists");
        }
    }

    public static void emptyCartCheck(CartInfoDto cartInfoDto) {
        if (cartInfoDto.getTotalCartPrice() == null || cartInfoDto.getTotalCartPrice() == 0) {
            throw new EmptyCartException("Cart is empty. Can not proceed with the order. Please add some products to" +
                    "the cart first.");
        }
    }
}