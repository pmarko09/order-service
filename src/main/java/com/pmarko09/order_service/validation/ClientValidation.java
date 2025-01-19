package com.pmarko09.order_service.validation;

import com.pmarko09.order_service.exception.client.ClientNotFoundException;
import com.pmarko09.order_service.exception.client.IllegalClientNameException;
import com.pmarko09.order_service.model.entity.Client;
import com.pmarko09.order_service.repository.ClientRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientValidation {

    public static void clientNameCheck(Client client) {
        if (client.getFirstname() == null || client.getFirstname().isEmpty()) {
            throw new IllegalClientNameException("Client name cannot be null");
        } else if (client.getLastname() == null || client.getLastname().isEmpty()) {
            throw new IllegalClientNameException("Client last name cannot be null");
        }
    }

    public static void clientEmailCheck(ClientRepository clientRepository, Client client) {
        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new IllegalClientNameException("Client with given email: " + client.getEmail() + " already exists.");
        }
    }

    public static Client clientExists(ClientRepository clientRepository, Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }
}