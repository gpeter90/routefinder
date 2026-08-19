package com.shipping.demo.usecase.getparcelsbyaddress;

import com.shipping.demo.domain.parcel.ParcelDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class GetParcelsByAddresseeResponse {

    private List<ParcelDto> parcels;
}
