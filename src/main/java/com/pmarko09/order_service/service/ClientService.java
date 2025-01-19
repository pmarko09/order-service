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

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public List<ClientDto> getAllClients(Pageable pageable) {
        log.info("Service method: fetching all clients");
        return clientRepository.findAllClients(pageable).stream()
                .map(clientMapper::toDto)
                .toList();
    }

    @Transactional
    public ClientDto addClient(Client client) {
        log.info("Service method: adding new client");
        ClientValidation.clientNameCheck(client);
        ClientValidation.clientEmailCheck(clientRepository, client);
        return clientMapper.toDto(clientRepository.save(client));
    }
}