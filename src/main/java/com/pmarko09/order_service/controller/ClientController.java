package com.pmarko09.order_service.controller;

import com.pmarko09.order_service.model.dto.ClientDto;
import com.pmarko09.order_service.model.entity.Client;
import com.pmarko09.order_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public List<ClientDto> getAllClients(Pageable pageable) {
        log.info("Endpoint GET called: /client");
        log.info("Fetching all clients");
        return clientService.getAllClients(pageable);
    }

    @PostMapping
    public ClientDto addClient(@RequestBody Client client) {
        log.info("Endpoint POST called: /client");
        log.info("Adding new client");
        return clientService.addClient(client);
    }
}