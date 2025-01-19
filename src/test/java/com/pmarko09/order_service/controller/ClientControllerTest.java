package com.pmarko09.order_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.order_service.model.dto.AddressDto;
import com.pmarko09.order_service.model.dto.ClientDto;
import com.pmarko09.order_service.model.entity.Address;
import com.pmarko09.order_service.model.entity.Client;
import com.pmarko09.order_service.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @MockBean
    ClientService clientService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllClients_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Star test: getAllClients_DataCorrect_ReturnStatus200");

        Pageable pageable = PageRequest.of(0, 10);
        AddressDto addressDto = new AddressDto(1L, "Wro", "Nowa", "111",
                "12", "50-120", "PL");
        ClientDto client1 = new ClientDto(1L, "Jan", "Kowalski", "jk@", addressDto);
        ClientDto client2 = new ClientDto(2L, "Maria", "Nowak", "mn@", null);

        when(clientService.getAllClients(pageable)).thenReturn(List.of(client1, client2));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/client?page=0&size=10")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstname").value("Jan"))
                .andExpect(jsonPath("$[0].lastname").value("Kowalski"))
                .andExpect(jsonPath("$[0].email").value("jk@"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].firstname").value("Maria"))
                .andExpect(jsonPath("$[1].lastname").value("Nowak"))
                .andExpect(jsonPath("$[1].email").value("mn@"));

        verify(clientService).getAllClients(pageable);
        log.info("Finish test: getAllClients_DataCorrect_ReturnStatus200");
    }

    @Test
    void addClient_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Star test: addClient_DataCorrect_ReturnStatus200");
        Address address = new Address(1L, "Wro", "Nowa", "111",
                "12", "50-120", "PL");
        AddressDto addressDto = new AddressDto(1L, "Wro", "Nowa", "111",
                "12", "50-120", "PL");
        Client client = new Client(1L, "Jan", "Kowalski", "jk@", address);
        ClientDto clientDto = new ClientDto(1L, "Jan", "Kowalski", "jk@", addressDto);

        when(clientService.addClient(client)).thenReturn(clientDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(client)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Jan"))
                .andExpect(jsonPath("$.lastname").value("Kowalski"))
                .andExpect(jsonPath("$.email").value("jk@"))
                .andExpect(jsonPath("$.address.city").value("Wro"))
                .andExpect(jsonPath("$.address.country").value("PL"));

        verify(clientService).addClient(client);
        log.info("Finish test: addClient_DataCorrect_ReturnStatus200");
    }
}