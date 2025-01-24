package com.pmarko09.order_service.mapper;

import com.pmarko09.order_service.model.dto.CartInfoDto;
import com.pmarko09.order_service.model.dto.OrderDto;
import com.pmarko09.order_service.model.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class OrderMapperTest {

    OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Test
    void mapOrderToDto() {
        log.info("Star test: mapOrderToDto");
        Address address = new Address(1L, "Wro", "Nowa", "111",
                "12", "50-120", "PL");
        Client client = new Client(1L, "Jan", "Kowalski", "jk@", address);
        CartInfoDto cartInfoDto = new CartInfoDto(1L, 9.9);
        Order order = new Order(5L, cartInfoDto, client, OrderDelivery.PACZKOMAT, PaymentForm.CASH,
                LocalDateTime.of(2024, 12, 12, 12, 12), 19.9);

        OrderDto result = orderMapper.toDto(order);

        assertNotNull(result);
        assertEquals(5L, result.getId());
        assertEquals(cartInfoDto, result.getCart());
        assertEquals(19.9, result.getFinalCost());
        assertEquals(OrderDelivery.PACZKOMAT, result.getDelivery());
        assertEquals(PaymentForm.CASH, result.getPaymentForm());
    }
}