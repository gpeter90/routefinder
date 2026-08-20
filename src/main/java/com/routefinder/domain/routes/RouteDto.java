package com.routefinder.domain.routes;

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
public class RouteDto {

    private Long id;
    private Long departureCityId;
    private Long arrivalCityId;
    private Integer distanceKm;
    private Integer travelTimeMin;
}
