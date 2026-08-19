package com.shipping.demo.usecase.getclients;

import com.shipping.demo.common.usecase.UseCase;
import com.shipping.demo.domain.client.ClientDto;
import com.shipping.demo.domain.client.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetClientsUseCase extends UseCase<Void, GetClientsResponse> {

    private final ClientService clientService;

    @Override
    protected GetClientsResponse executeBusinessLogic(Void request) {
        List<ClientDto> clients = clientService.findAll();

        return GetClientsResponse.builder()
                .clients(clients)
                .build();
    }
}
