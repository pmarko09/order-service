package com.pmarko09.order_service.service;

import com.pmarko09.order_service.mapper.ClientMapper;
import com.pmarko09.order_service.model.dto.AddressDto;
import com.pmarko09.order_service.model.dto.ClientDto;
import com.pmarko09.order_service.model.entity.Address;
import com.pmarko09.order_service.model.entity.Client;
import com.pmarko09.order_service.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
public class ClientServiceTest {

    ClientRepository clientRepository;
    ClientMapper clientMapper;
    ClientService clientService;

    @BeforeEach
    void setup() {
        this.clientRepository = Mockito.mock(ClientRepository.class);
        this.clientMapper = Mappers.getMapper(ClientMapper.class);
        this.clientService = new ClientService(clientRepository, clientMapper);
    }

    @Test
    void getAllClients_DataCorrect_ClientDtosReturned() {
        log.info("Start test: getAllClients_DataCorrect_ClientDtosReturned");
        Pageable pageable = PageRequest.of(0, 10);
        Address address = new Address();
        address.setId(1L);
        address.setCity("WRO");
        address.setCountry("PL");
        Client client = new Client(1L, "Jan", "Kowal", "jk@", address);

        when(clientRepository.findAllClients(pageable)).thenReturn(List.of(client));

        List<ClientDto> result = clientService.getAllClients(pageable);

        assertNotNull(result);
        assertEquals(1L, result.getFirst().getId());
        assertEquals("Jan", result.getFirst().getFirstname());
        assertEquals("Kowal", result.getFirst().getLastname());
        assertEquals("PL", result.getFirst().getAddress().getCountry());

        log.info("Finish test: getAllClients_DataCorrect_ClientDtosReturned");
    }

    @Test
    void addClient_DataCorrect_ClientDtoReturned() {
        log.info("Start test: addClient_DataCorrect_ClientDtoReturned");
        Address address = new Address();
        address.setId(1L);
        address.setCity("WRO");
        address.setCountry("PL");

        Client client = new Client(1L, "Jan", "Kowal", "jk@", address);

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDto result = clientService.addClient(client);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jan", result.getFirstname());
        assertEquals("Kowal", result.getLastname());
        assertEquals("jk@", result.getEmail());
        assertEquals("WRO", result.getAddress().getCity());
        assertEquals("PL", result.getAddress().getCountry());
        log.info("Finish test: addClient_DataCorrect_ClientDtoReturned");
    }
}