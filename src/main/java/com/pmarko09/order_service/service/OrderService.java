package com.pmarko09.order_service.service;

import com.pmarko09.order_service.config.InvoiceNumberGenerator;
import com.pmarko09.order_service.mapper.InvoiceMapper;
import com.pmarko09.order_service.mapper.OrderMapper;
import com.pmarko09.order_service.model.dto.InvoiceDto;
import com.pmarko09.order_service.model.dto.CartInfoDto;
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

/**
 * Service class for handling order-related operations such as processing orders,
 * retrieving client orders, generating invoices, and fetching invoices.
 */

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

    /**
     * Processes an order based on the provided OrderRequestDto.
     *
     * @param orderRequest the DTO containing order details such as client ID, cart ID,
     *                     delivery type, and payment form.
     * @return the saved order as a OrderDto.
     */
    @Transactional
    public OrderDto processOrder(OrderRequestDto orderRequest) {
        log.info("OrderService method: processing with order: {}", orderRequest);
        Client client = ClientValidation.clientExists(clientRepository, orderRequest.getClientId());

        CartInfoDto cart = cartServiceClient.getCartById(orderRequest.getCartId());
        OrderValidation.emptyCartCheck(cart);

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

    /**
     * Retrieves a list of orders placed by a specific client.
     *
     * @param clientId the ID of the client whose orders are to be fetched.
     * @return a list of OrderDto representing the client's orders.
     */
    public List<OrderDto> getOrdersByClient(Long clientId) {
        log.info("OrderService method: fetching orders for client with ID: {}", clientId);
        ClientValidation.clientExists(clientRepository, clientId);
        return orderRepository.findByClientId(clientId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    /**
     * Generates an invoice for a given order ID.
     *
     * @param orderId the ID of the order for which the invoice is to be generated.
     * @return the generated invoice as a InvoiceDto..
     */
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

    /**
     * Fetches an invoice for a given order ID.
     *
     * @param orderId the ID of the order whose invoice is to be fetched.
     * @return the invoice as a InvoiceDto.
     */
    public InvoiceDto getInvoiceByOrderId(Long orderId) {
        log.info("OrderService method: fetching invoice for order ID: {}", orderId);
        OrderValidation.orderExists(orderRepository, orderId);
        Invoice invoice = InvoiceValidation.invoiceExists(invoiceRepository, orderId);
        return invoiceMapper.toDto(invoice);
    }
}