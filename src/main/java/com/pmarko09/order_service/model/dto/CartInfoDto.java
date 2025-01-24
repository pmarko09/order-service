package com.pmarko09.order_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CartInfoDto {

    @JsonProperty("id")
    private Long cartId;

    @JsonProperty("totalPrice")
    private Double totalCartPrice;
}