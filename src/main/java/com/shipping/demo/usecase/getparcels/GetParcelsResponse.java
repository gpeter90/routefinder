package com.shipping.demo.usecase.getparcels;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class GetParcelsResponse {

    private List<?> parcels;
}
