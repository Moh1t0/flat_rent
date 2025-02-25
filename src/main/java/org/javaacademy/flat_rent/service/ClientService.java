package org.javaacademy.flat_rent.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rent.dto.client.ClientDto;
import org.javaacademy.flat_rent.entity.Client;
import org.javaacademy.flat_rent.mapper.ClientMapper;
import org.javaacademy.flat_rent.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientDto save(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        clientRepository.save(client);
        return clientMapper.toDto(client);
    }
}
