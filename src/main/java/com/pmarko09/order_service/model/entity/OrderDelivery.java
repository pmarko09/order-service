package com.pmarko09.order_service.model.entity;

import lombok.*;

@AllArgsConstructor
@Getter
public enum OrderDelivery {
    SELF_PICK_UP(0.0),
    PACZKOMAT(10.0),
    COURIER(15.0),
    COURIER_PRIORITY(20.0);

    private final double cost;
}