package com.pmarko09.order_service.controller;

import com.pmarko09.order_service.model.dto.ClientDto;
import com.pmarko09.order_service.model.entity.Client;
import com.pmarko09.order_service.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for client operations.
 * Provides endpoints for retrieving a list of clients and adding new clients.
 */

@Slf4j
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Tag(name = "Client Management", description = "Endpoints for managing clients")
public class ClientController {

    private final ClientService clientService;

    /**
     * Retrieves a list of clients with pagination.
     *
     * @param pageable Pagination information.
     * @return A list of ClientDto objects representing the retrieved clients.
     */
    @GetMapping
    @Operation(summary = "Get all clients", description = "Retrieve a paginated list of clients")
    public List<ClientDto> getAllClients(Pageable pageable) {
        log.info("Endpoint GET called: /client");
        log.info("Fetching all clients");
        return clientService.getAllClients(pageable);
    }

    /**
     * Adds a new client.
     *
     * @param client The client data to be added.
     * @return A ClientDto object representing the created client.
     */
    @PostMapping
    @Operation(summary = "Add a new client", description = "Create a new client in the system")
    public ClientDto addClient(@RequestBody Client client) {
        log.info("Endpoint POST called: /client");
        log.info("Adding new client");
        return clientService.addClient(client);
    }
}