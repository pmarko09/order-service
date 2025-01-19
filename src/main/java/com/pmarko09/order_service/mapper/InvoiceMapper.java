package com.pmarko09.order_service.mapper;

import com.pmarko09.order_service.model.dto.InvoiceDto;
import com.pmarko09.order_service.model.entity.Invoice;
import com.pmarko09.order_service.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(source = "order", target = "orderId", qualifiedByName = "mapOrderToId")
    InvoiceDto toDto(Invoice invoice);

    @Named("mapOrderToId")
    default Long mapOrderToId(Order order) {
        return order != null ? order.getId() : null;
    }
}