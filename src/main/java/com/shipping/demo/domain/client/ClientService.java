package com.shipping.demo.domain.client;

import com.shipping.demo.common.domain.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService extends DomainService<ClientDto> {
    private final ClientRepository clientRepository;

    @Override
    protected void update(ClientDto clientDto) {
        clientRepository.save(mapDtoToEntity(clientDto));
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
