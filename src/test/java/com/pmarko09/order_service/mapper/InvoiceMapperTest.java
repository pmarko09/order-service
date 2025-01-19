package com.pmarko09.order_service.mapper;

import com.pmarko09.order_service.model.dto.InvoiceDto;
import com.pmarko09.order_service.model.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class InvoiceMapperTest {

    InvoiceMapper invoiceMapper = Mappers.getMapper(InvoiceMapper.class);

    @Test
    void mapInvoiceToDto() {
        log.info("Star test: mapInvoiceToDto");

        Client client = new Client(1L, "Jan", "Kowalski", "jk@", null);
        CartInfo cartInfo = new CartInfo(1L, 9.9);
        Order order = new Order(5L, cartInfo, client, OrderDelivery.PACZKOMAT, PaymentForm.CASH,
                LocalDateTime.of(2024, 12, 12, 12, 12), 19.9);
        Invoice invoice = new Invoice(1L, order, "1234",
                LocalDateTime.of(2024, 12, 22, 12, 12));

        InvoiceDto result = invoiceMapper.toDto(invoice);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(5L, result.getOrderId());
        assertEquals("1234", result.getInvoiceNumber());
        assertEquals(LocalDateTime.of(2024, 12, 22, 12, 12), result.getIssueDate());
        log.info("Finish test: mapInvoiceToDto");
    }
}
