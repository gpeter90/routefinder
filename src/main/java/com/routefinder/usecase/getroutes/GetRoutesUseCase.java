package com.routefinder.usecase.getroutes;

import com.routefinder.common.usecase.UseCase;
import com.routefinder.domain.cities.CityDto;
import com.routefinder.domain.cities.CityService;
import com.routefinder.domain.routes.RouteDto;
import com.routefinder.domain.routes.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetRoutesUseCase extends UseCase<Void, GetRoutesResponse> {

    private final RouteService routeService;
    private final CityService cityService;

    @Override
    protected GetRoutesResponse executeBusinessLogic(Void request) {
        List<RouteDto> routes = routeService.findAll();
        Map<Long, CityDto> citiesById = cityService.findAll().stream()
                .collect(Collectors.toMap(CityDto::getId, Function.identity()));

        List<RouteDetailDto> routeDetails = routes.stream()
                .map(route -> generateRouteDetail(route, citiesById))
                .toList();

        return GetRoutesResponse.builder()
                .routes(routeDetails)
                .build();
    }

    private RouteDetailDto generateRouteDetail(RouteDto route, Map<Long, CityDto> citiesById) {
        CityDto departureCity = citiesById.get(route.getDepartureCityId());
        CityDto arrivalCity = citiesById.get(route.getArrivalCityId());

        return RouteDetailDto.builder()
                .id(route.getId())
                .departureCityName(departureCity.getCityName())
                .arrivalCityName(arrivalCity.getCityName())
                .distanceKm(route.getDistanceKm())
                .travelTimeMin(route.getTravelTimeMin())
                .build();
    }
}
