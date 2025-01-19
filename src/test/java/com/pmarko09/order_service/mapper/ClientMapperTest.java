package com.pmarko09.order_service.mapper;

import com.pmarko09.order_service.model.dto.ClientDto;
import com.pmarko09.order_service.model.entity.Address;
import com.pmarko09.order_service.model.entity.Client;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ClientMapperTest {

    ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @Test
    void mapClientToDto() {
        log.info("Star test: mapClientToDto");
        Address address = new Address(1L, "Wro", "Nowa", "111",
                "12", "50-120", "PL");
        Client client = new Client(1L, "Jan", "Kowalski", "jk@", address);

        ClientDto result = clientMapper.toDto(client);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jan", result.getFirstname());
        assertEquals("Kowalski", result.getLastname());
        assertEquals("jk@", result.getEmail());
        assertEquals("Wro", result.getAddress().getCity());
        assertEquals("PL", result.getAddress().getCountry());
        log.info("Finish test: mapClientToDto");
    }
}