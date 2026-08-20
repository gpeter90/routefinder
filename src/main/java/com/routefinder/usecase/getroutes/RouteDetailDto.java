package com.routefinder.usecase.getroutes;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RouteDetailDto {

    private Long id;
    private String departureCityName;
    private String arrivalCityName;
    private int distanceKm;
    private int travelTimeMin;
}
