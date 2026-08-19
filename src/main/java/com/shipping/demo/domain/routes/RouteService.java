package com.shipping.demo.domain.routes;

import com.shipping.demo.common.domain.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService extends DomainService<RouteDto> {
    private final RouteRepository routeRepository;

    public List<RouteDto> findAll() {
        return routeRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .toList();
    }

    @Override
    protected void update(RouteDto routeDto) {
        routeRepository.save(mapDtoToEntity(routeDto));
    }

    private RouteDto mapEntityToDto(Route route) {
        return RouteDto.builder()
                .id(route.getId())
                .departureCityId(route.getDepartureCityId())
                .arrivalCityId(route.getArrivalCityId())
                .distanceKm(route.getDistanceKm())
                .travelTimeMin(route.getTravelTimeMin())
                .build();
    }

    private Route mapDtoToEntity(RouteDto routeDto) {
        return Route.builder()
                .departureCityId(routeDto.getDepartureCityId())
                .arrivalCityId(routeDto.getArrivalCityId())
                .distanceKm(routeDto.getDistanceKm())
                .travelTimeMin(routeDto.getTravelTimeMin())
                .build();
    }
}
