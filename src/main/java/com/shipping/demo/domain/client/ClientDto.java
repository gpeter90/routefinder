package com.shipping.demo.domain.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class ClientDto {
    private String clientName;
    private String address;
    private String zipcode;
    private String city;
    private String phone;
    private String email;
}
