package com.shipping.demo.usecase.getparcelsbysender;

import com.shipping.demo.domain.parcel.ParcelDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class GetParcelsBySenderResponse {

    private List<ParcelDto> parcels;
}
