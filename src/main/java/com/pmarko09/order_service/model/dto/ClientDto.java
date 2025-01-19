package com.pmarko09.order_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private AddressDto address;
}