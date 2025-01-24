package com.pmarko09.order_service.service;

import com.pmarko09.order_service.mapper.ClientMapper;
import com.pmarko09.order_service.model.dto.ClientDto;
import com.pmarko09.order_service.model.entity.Client;
import com.pmarko09.order_service.repository.ClientRepository;
import com.pmarko09.order_service.validation.ClientValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for client operations.
 * Provides methods for retrieving a list of clients and adding new clients.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    /**
     * Retrieves a list of clients with pagination.
     *
     * @param pageable Pagination information.
     * @return A list of ClientDto objects representing the retrieved clients.
     */
    public List<ClientDto> getAllClients(Pageable pageable) {
        log.info("Service method: fetching all clients");
        return clientRepository.findAllClients(pageable).stream()
                .map(clientMapper::toDto)
                .toList();
    }

    /**
     * Adds a new client.
     * Performs validations on client name and email before saving the client.
     *
     * @param client The client data to be added.
     * @return A ClientDto object representing the created client.
     */
    @Transactional
    public ClientDto addClient(Client client) {
        log.info("Service method: adding new client");
        ClientValidation.clientNameCheck(client);
        ClientValidation.clientEmailCheck(clientRepository, client);
        return clientMapper.toDto(clientRepository.save(client));
    }
}