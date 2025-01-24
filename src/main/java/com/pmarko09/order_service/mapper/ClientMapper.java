package com.pmarko09.order_service.mapper;

import com.pmarko09.order_service.model.dto.AddressDto;
import com.pmarko09.order_service.model.dto.ClientDto;
import com.pmarko09.order_service.model.entity.Address;
import com.pmarko09.order_service.model.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toDto(Client client);

    AddressDto toDto(Address address);
}