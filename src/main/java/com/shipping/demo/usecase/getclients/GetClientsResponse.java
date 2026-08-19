package com.shipping.demo.usecase.getclients;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class GetClientsResponse {

    private List<?> clients;
}
