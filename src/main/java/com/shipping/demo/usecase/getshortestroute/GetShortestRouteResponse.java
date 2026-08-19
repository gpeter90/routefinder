package com.shipping.demo.usecase.getshortestroute;

import com.shipping.demo.domain.cities.CityDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class GetShortestRouteResponse {

    private List<CityDto> path;
    private int totalDistanceKm;
    private int totalTravelTimeMin;
}
