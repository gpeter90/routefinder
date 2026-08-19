package com.shipping.demo.usecase.getroutes;

import com.shipping.demo.common.usecase.UseCase;
import com.shipping.demo.domain.cities.CityDto;
import com.shipping.demo.domain.cities.CityService;
import com.shipping.demo.domain.routes.RouteDto;
import com.shipping.demo.domain.routes.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetRoutesUseCase extends UseCase<Void, GetRoutesResponse> {

    private final RouteService routeService;
    private final CityService cityService;

    @Override
    protected GetRoutesResponse executeBusinessLogic(Void request) {
        List<RouteDto> routes = routeService.findAll();

        List<RouteDetailDto> routeDetails = routes.stream()
                .map(this::mapToRouteDetail)
                .toList();

        return GetRoutesResponse.builder()
                .routes(routeDetails)
                .build();
    }

    private RouteDetailDto mapToRouteDetail(RouteDto route) {
        CityDto departureCity = cityService.findById(route.getDepartureCityId());
        CityDto arrivalCity = cityService.findById(route.getArrivalCityId());

        return RouteDetailDto.builder()
                .id(route.getId())
                .departureCityName(departureCity.getCityName())
                .arrivalCityName(arrivalCity.getCityName())
                .distanceKm(route.getDistanceKm())
                .travelTimeMin(route.getTravelTimeMin())
                .build();
    }
}
