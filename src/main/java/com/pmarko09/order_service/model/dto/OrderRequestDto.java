package com.pmarko09.order_service.model.dto;

import com.pmarko09.order_service.model.entity.OrderDelivery;
import com.pmarko09.order_service.model.entity.PaymentForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private Long clientId;
    private Long cartId;
    private OrderDelivery delivery;
    private PaymentForm paymentForm;
}