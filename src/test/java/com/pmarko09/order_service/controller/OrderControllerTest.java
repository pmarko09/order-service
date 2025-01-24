package com.pmarko09.order_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.order_service.model.dto.InvoiceDto;
import com.pmarko09.order_service.model.dto.OrderDto;
import com.pmarko09.order_service.model.dto.OrderRequestDto;
import com.pmarko09.order_service.model.dto.CartInfoDto;
import com.pmarko09.order_service.model.entity.OrderDelivery;
import com.pmarko09.order_service.model.entity.PaymentForm;
import com.pmarko09.order_service.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @MockBean
    OrderService orderService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void processOrder_DataCorrect_Status200Returned() throws Exception {
        log.info("Start test: processOrder_DataCorrect_Status200Returned");
        CartInfoDto cartInfoDto = new CartInfoDto(2L, 90.0);
        OrderRequestDto orderRequestDto = new OrderRequestDto(11L, 2L, OrderDelivery.PACZKOMAT, PaymentForm.CASH);
        OrderDto orderDto = new OrderDto(1L, cartInfoDto, 11L, OrderDelivery.PACZKOMAT, PaymentForm.CASH, 100.0);

        when(orderService.processOrder(orderRequestDto)).thenReturn(orderDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/order")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cart.id").value(2L))
                .andExpect(jsonPath("$.cart.totalPrice").value(90.0))
                .andExpect(jsonPath("$.clientId").value(11L))
                .andExpect(jsonPath("$.delivery").value(OrderDelivery.PACZKOMAT.toString()))
                .andExpect(jsonPath("$.paymentForm").value(PaymentForm.CASH.toString()))
                .andExpect(jsonPath("$.finalCost").value(100.0));

        verify(orderService).processOrder(orderRequestDto);
        log.info("Finish test: processOrder_DataCorrect_Status200Returned");
    }

    @Test
    void getOrdersByClientId_DataCorrect_Status200Returned() throws Exception {
        log.info("Start test: getOrdersByClientId_DataCorrect_Status200Returned");
        CartInfoDto cartInfoDto = new CartInfoDto(2L, 90.0);
        OrderDto orderDto = new OrderDto(1L, cartInfoDto, 11L, OrderDelivery.PACZKOMAT, PaymentForm.CASH, 100.0);

        when(orderService.getOrdersByClient(11L)).thenReturn(List.of(orderDto));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/order?clientId=11"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].cart.id").value(2L))
                .andExpect(jsonPath("$[0].cart.totalPrice").value(90.0))
                .andExpect(jsonPath("$[0].clientId").value(11L))
                .andExpect(jsonPath("$[0].delivery").value(OrderDelivery.PACZKOMAT.toString()))
                .andExpect(jsonPath("$[0].paymentForm").value(PaymentForm.CASH.toString()))
                .andExpect(jsonPath("$[0].finalCost").value(100.0));

        verify(orderService).getOrdersByClient(11L);
        log.info("Finish test: getOrdersByClientId_DataCorrect_Status200Returned");
    }

    @Test
    void generateInvoice_DataCorrect_Status200Returned() throws Exception {
        log.info("Start test: generateInvoice_DataCorrect_Status200Returned");
        InvoiceDto invoiceDto = new InvoiceDto(1L, 6L, "123", LocalDateTime.of(2024, 12, 12, 12, 12));

        when(orderService.generateInvoice(6L)).thenReturn(invoiceDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/order/invoice/6"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.orderId").value(6L))
                .andExpect(jsonPath("$.invoiceNumber").value("123"))
                .andExpect(jsonPath("$.issueDate").value("2024-12-12T12:12:00"));

        verify(orderService).generateInvoice(6L);
        log.info("Finish test: generateInvoice_DataCorrect_Status200Returned");
    }

    @Test
    void getInvoiceByOrderId_DataCorrect_Status200Returned() throws Exception {
        log.info("Start test: getInvoiceByOrderId_DataCorrect_Status200Returned");
        InvoiceDto invoiceDto = new InvoiceDto(1L, 6L, "123", LocalDateTime.of(2024, 12, 12, 12, 12));

        when(orderService.getInvoiceByOrderId(6L)).thenReturn(invoiceDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/order/invoice/6"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.orderId").value(6L))
                .andExpect(jsonPath("$.invoiceNumber").value("123"))
                .andExpect(jsonPath("$.issueDate").value("2024-12-12T12:12:00"));

        verify(orderService).getInvoiceByOrderId(6L);
        log.info("Finish test: getInvoiceByOrderId_DataCorrect_Status200Returned");
    }
}