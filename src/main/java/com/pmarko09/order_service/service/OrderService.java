package com.pmarko09.order_service.service;

import com.pmarko09.order_service.config.InvoiceNumberGenerator;
import com.pmarko09.order_service.mapper.InvoiceMapper;
import com.pmarko09.order_service.mapper.OrderMapper;
import com.pmarko09.order_service.model.dto.InvoiceDto;
import com.pmarko09.order_service.model.entity.CartInfo;
import com.pmarko09.order_service.model.entity.Client;
import com.pmarko09.order_service.model.entity.Invoice;
import com.pmarko09.order_service.model.entity.Order;
import com.pmarko09.order_service.repository.ClientRepository;
import com.pmarko09.order_service.repository.InvoiceRepository;
import com.pmarko09.order_service.repository.OrderRepository;
import com.pmarko09.order_service.client.CartServiceClient;
import com.pmarko09.order_service.model.dto.OrderDto;
import com.pmarko09.order_service.model.dto.OrderRequestDto;
import com.pmarko09.order_service.validation.ClientValidation;
import com.pmarko09.order_service.validation.InvoiceValidation;
import com.pmarko09.order_service.validation.OrderValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartServiceClient cartServiceClient;
    private final ClientRepository clientRepository;
    private final OrderMapper orderMapper;
    private final InvoiceNumberGenerator invoiceNumberGenerator;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceRepository invoiceRepository;

    @Transactional
    public OrderDto processOrder(OrderRequestDto orderRequest) {
        log.info("OrderService method: processing with order: {}", orderRequest);
        Client client = ClientValidation.clientExists(clientRepository, orderRequest.getClientId());

        CartInfo cart = cartServiceClient.getCartById(orderRequest.getCartId());

        Order order = new Order();
        order.setClient(client);
        order.setCart(cart);
        order.setDelivery(orderRequest.getDelivery());
        order.setPaymentForm(orderRequest.getPaymentForm());
        order.setCreatedAt(LocalDateTime.now());

        double deliveryCost = orderRequest.getDelivery().getCost();
        double totalOrderCost = cart.getTotalCartPrice() + deliveryCost;

        order.setFinalCost(totalOrderCost);

        return orderMapper.toDto(orderRepository.save(order));
    }

    public List<OrderDto> getOrdersByClient(Long clientId) {
        log.info("OrderService method: fetching order for client with ID: {}", clientId);
        ClientValidation.clientExists(clientRepository, clientId);
        return orderRepository.findByClientId(clientId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Transactional
    public InvoiceDto generateInvoice(Long orderId) {
        log.info("OrderService method: generating invoice for order ID: {}", orderId);
        Order order = OrderValidation.orderExists(orderRepository, orderId);
        OrderValidation.orderWithInvoiceCheck(invoiceRepository, orderId);

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setInvoiceNumber(invoiceNumberGenerator.generate());
        invoice.setIssueDate(LocalDateTime.now());

        return invoiceMapper.toDto(invoiceRepository.save(invoice));
    }

    public InvoiceDto getInvoiceByOrderId(Long orderId) {
        log.info("OrderService method: fetching invoice for order ID: {}", orderId);
        OrderValidation.orderExists(orderRepository, orderId);
        Invoice invoice = InvoiceValidation.invoiceExists(invoiceRepository, orderId);
        return invoiceMapper.toDto(invoice);
    }
}