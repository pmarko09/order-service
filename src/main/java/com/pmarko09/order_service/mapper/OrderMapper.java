package com.pmarko09.order_service.mapper;

import com.pmarko09.order_service.model.dto.OrderDto;
import com.pmarko09.order_service.model.entity.Client;
import com.pmarko09.order_service.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "client", target = "clientId", qualifiedByName = "mapClientToId")
    OrderDto toDto(Order order);

    @Named("mapClientToId")
    default Long mapClientToId(Client client) {
        return client != null ? client.getId() : null;
    }
}