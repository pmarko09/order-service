package com.pmarko09.order_service.model.dto;

import com.pmarko09.order_service.model.entity.OrderDelivery;
import com.pmarko09.order_service.model.entity.PaymentForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private CartInfoDto cart;
    private Long clientId;
    private OrderDelivery delivery;
    private PaymentForm paymentForm;
    private Double finalCost;
}