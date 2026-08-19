package com.shipping.demo.domain.client;

import com.shipping.demo.common.domain.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService extends DomainService<ClientDto> {
    private final ClientRepository clientRepository;

    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .toList();
    }

    @Override
    protected void update(ClientDto clientDto) {
        clientRepository.save(mapDtoToEntity(clientDto));
    }

    private ClientDto mapEntityToDto(Client client) {
        return ClientDto.builder()
                .clientName(client.getClientName())
                .address(client.getAddress())
                .zipcode(client.getZipcode())
                .city(client.getCity())
                .phone(client.getPhone())
                .email(client.getEmail())
                .build();
    }

    private Client mapDtoToEntity(ClientDto clientDto) {
        return Client.builder()
                .clientName(clientDto.getClientName())
                .address(clientDto.getAddress())
                .zipcode(clientDto.getZipcode())
                .city(clientDto.getCity())
                .phone(clientDto.getPhone())
                .email(clientDto.getEmail())
                .build();
    }
}
