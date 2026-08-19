package com.shipping.demo.usecase.getclients;

import com.shipping.demo.domain.client.ClientDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class GetClientsResponse {

    private List<ClientDto> clients;
}
