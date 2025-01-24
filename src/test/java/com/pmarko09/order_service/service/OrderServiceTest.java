package com.pmarko09.order_service.service;

import com.pmarko09.order_service.client.CartServiceClient;
import com.pmarko09.order_service.config.InvoiceNumberGenerator;
import com.pmarko09.order_service.mapper.InvoiceMapper;
import com.pmarko09.order_service.mapper.OrderMapper;
import com.pmarko09.order_service.model.dto.CartInfoDto;
import com.pmarko09.order_service.model.dto.InvoiceDto;
import com.pmarko09.order_service.model.dto.OrderDto;
import com.pmarko09.order_service.model.dto.OrderRequestDto;
import com.pmarko09.order_service.model.entity.*;
import com.pmarko09.order_service.repository.ClientRepository;
import com.pmarko09.order_service.repository.InvoiceRepository;
import com.pmarko09.order_service.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class OrderServiceTest {

    OrderRepository orderRepository;
    CartServiceClient cartServiceClient;
    ClientRepository clientRepository;
    OrderMapper orderMapper;
    InvoiceNumberGenerator invoiceNumberGenerator;
    InvoiceMapper invoiceMapper;
    InvoiceRepository invoiceRepository;
    OrderService orderService;

    @BeforeEach
    void setup() {
        this.orderRepository = Mockito.mock(OrderRepository.class);
        this.cartServiceClient = Mockito.mock(CartServiceClient.class);
        this.clientRepository = Mockito.mock(ClientRepository.class);
        this.orderMapper = Mappers.getMapper(OrderMapper.class);
        this.invoiceNumberGenerator = Mockito.mock(InvoiceNumberGenerator.class);
        this.invoiceMapper = Mappers.getMapper(InvoiceMapper.class);
        this.invoiceRepository = Mockito.mock(InvoiceRepository.class);
        this.orderService = new OrderService(orderRepository, cartServiceClient,
                clientRepository, orderMapper, invoiceNumberGenerator, invoiceMapper,
                invoiceRepository);
    }

    @Test
    void processOrder_DataCorrect_OrderDtoReturned() {
        log.info("Start test: processOrder_DataCorrect_OrderDtoReturned");
        Address address = new Address();
        address.setId(1L);
        address.setCity("WRO");
        address.setCountry("PL");
        Client client = new Client(1L, "Jan", "Kowal", "jk@", address);
        CartInfoDto cartInfoDto = new CartInfoDto(3L, 99.9);
        Order order = new Order(10L, cartInfoDto, client, OrderDelivery.PACZKOMAT,
                PaymentForm.CASH, LocalDateTime.of(2024, 12, 12, 12, 12),
                109.9);
        OrderRequestDto orderRequestDto = new OrderRequestDto(1L, 3L, OrderDelivery.PACZKOMAT, PaymentForm.CASH);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(cartServiceClient.getCartById(3L)).thenReturn(cartInfoDto);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto result = orderService.processOrder(orderRequestDto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(3L, result.getCart().getCartId());
        assertEquals(1L, result.getClientId());
        assertEquals(OrderDelivery.PACZKOMAT, result.getDelivery());
        assertEquals(PaymentForm.CASH, result.getPaymentForm());
        assertEquals(109.9, result.getFinalCost());

        Mockito.verify(clientRepository).findById(1L);
        Mockito.verify(cartServiceClient).getCartById(3L);
        Mockito.verify(orderRepository).save(any(Order.class));
        log.info("Finish test: processOrder_DataCorrect_OrderDtoReturned");
    }

    @Test
    void getOrdersByClient_DataCorrect_OrderDtoReturned() {
        log.info("Start test: getOrdersByClient_DataCorrect_OrderDtoReturned");
        CartInfoDto cartInfoDto = new CartInfoDto(3L, 99.9);
        Address address = new Address();
        address.setId(1L);
        address.setCity("WRO");
        address.setCountry("PL");
        Client client = new Client(5L, "Jan", "Kowal", "jk@", address);
        Order order = new Order(10L, cartInfoDto, client, OrderDelivery.PACZKOMAT,
                PaymentForm.CASH, LocalDateTime.of(2024, 12, 12, 12, 12),
                109.9);

        when(clientRepository.findById(5L)).thenReturn(Optional.of(client));
        when(orderRepository.findByClientId(5L)).thenReturn(List.of(order));

        List<OrderDto> result = orderService.getOrdersByClient(5L);

        assertNotNull(result);
        assertEquals(10L, result.getFirst().getId());
        assertEquals(3L, result.getFirst().getCart().getCartId());
        assertEquals(5L, result.getFirst().getClientId());
        assertEquals(OrderDelivery.PACZKOMAT, result.getFirst().getDelivery());

        Mockito.verify(clientRepository).findById(5L);
        Mockito.verify(orderRepository).findByClientId(5L);
        log.info("Finish test: getOrdersByClient_DataCorrect_OrderDtoReturned");
    }

    @Test
    void generateInvoice_DataCorrect_OrderDtoReturned() {
        log.info("Start test: generateInvoice_DataCorrect_OrderDtoReturned");
        CartInfoDto cartInfoDto = new CartInfoDto(3L, 99.9);
        Address address = new Address();
        address.setId(1L);
        address.setCity("WRO");
        address.setCountry("PL");
        Client client = new Client(5L, "Jan", "Kowal", "jk@", address);
        Order order = new Order(10L, cartInfoDto, client, OrderDelivery.PACZKOMAT,
                PaymentForm.CASH, LocalDateTime.of(2024, 12, 12, 12, 12),
                109.9);
        Invoice invoice = new Invoice(4L, order, "123",
                LocalDateTime.of(2024, 11, 12, 12, 12)
        );

        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);

        InvoiceDto result = orderService.generateInvoice(10L);

        assertNotNull(result);
        assertEquals(4L, result.getId());
        assertEquals(10L, result.getOrderId());
        assertEquals("123", result.getInvoiceNumber());
        assertEquals(LocalDateTime.of(2024, 11, 12, 12, 12), result.getIssueDate());

        Mockito.verify(orderRepository).findById(10L);
        Mockito.verify(invoiceRepository).save(any(Invoice.class));
        log.info("Start test: generateInvoice_DataCorrect_OrderDtoReturned");
    }

    @Test
    void getInvoiceByOrderId_DataCorrect_OrderDtoReturned() {
        log.info("Start test: getInvoiceByOrderId_DataCorrect_OrderDtoReturned");
        CartInfoDto cartInfoDto = new CartInfoDto(3L, 99.9);
        Address address = new Address();
        address.setId(1L);
        address.setCity("WRO");
        address.setCountry("PL");
        Client client = new Client(5L, "Jan", "Kowal", "jk@", address);
        Order order = new Order(10L, cartInfoDto, client, OrderDelivery.PACZKOMAT,
                PaymentForm.CASH, LocalDateTime.of(2024, 12, 12, 12, 12),
                109.9);
        Invoice invoice = new Invoice(4L, order, "123",
                LocalDateTime.of(2024, 11, 12, 12, 12)
        );

        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));
        when(invoiceRepository.findByOrderId(10L)).thenReturn(Optional.of(invoice));

        InvoiceDto result = orderService.getInvoiceByOrderId(10L);

        assertNotNull(result);
        assertEquals(4L, result.getId());
        assertEquals(10L, result.getOrderId());
        assertEquals("123", result.getInvoiceNumber());
        assertEquals(LocalDateTime.of(2024, 11, 12, 12, 12), result.getIssueDate());

        Mockito.verify(orderRepository).findById(10L);
        Mockito.verify(invoiceRepository).findByOrderId(10L);
        log.info("Finish test: getInvoiceByOrderId_DataCorrect_OrderDtoReturned");
    }
}

