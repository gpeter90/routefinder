package com.shipping.demo.usecase.getshortestroute;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DijkstraResult {

    private List<Long> pathCityIds;
    private int totalDistanceKm;
    private int totalTravelTimeMin;
}
