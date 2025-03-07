package org.javaacademy.flat_rent.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.flat_rent.dto.client.ClientDto;
import org.javaacademy.flat_rent.entity.Client;
import org.javaacademy.flat_rent.mapper.ClientMapper;
import org.javaacademy.flat_rent.repository.BookingRepository;
import org.javaacademy.flat_rent.repository.ClientRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final BookingRepository bookingRepository;

    @Transactional
    public ClientDto save(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Transactional
    public void deleteClientById(Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Client с таким id не найден!"));
        bookingRepository.deleteByClient(client);
        clientRepository.delete(client);
    }
}
