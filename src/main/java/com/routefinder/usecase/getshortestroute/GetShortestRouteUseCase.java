package com.routefinder.usecase.getshortestroute;

import com.routefinder.common.exception.InvalidParameterException;
import com.routefinder.common.exception.NotFoundException;
import com.routefinder.common.usecase.UseCase;
import com.routefinder.common.usecase.UseCaseWithExtraValidation;
import com.routefinder.common.validator.MandatoryFieldValidator;
import com.routefinder.common.validator.Validator;
import com.routefinder.domain.cities.CityDto;
import com.routefinder.domain.cities.CityService;
import com.routefinder.domain.routes.RouteDto;
import com.routefinder.domain.routes.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetShortestRouteUseCase extends UseCase<GetShortestRouteRequest, GetShortestRouteResponse>
        implements UseCaseWithExtraValidation<GetShortestRouteRequest> {

    public static final String DEPARTURE_AND_ARRIVAL_CITIES_MUST_BE_DIFFERENT =
            "Departure and arrival cities must be different!";
    public static final String DEPARTURE_CITY_NOT_FOUND_WITH_ID = "Departure city not found with id: %s";
    public static final String ARRIVAL_CITY_NOT_FOUND_WITH_ID = "Arrival city not found with id: %s";
    public static final String NO_ROUTE_FOUND_BETWEEN_THE_SPECIFIED_CITIES =
            "No route found between the specified cities!";
    public static final String CITY_NOT_FOUND = "City not found with id: %s";
    private final CityService cityService;
    private final RouteService routeService;
    private final DijkstraPathFinder dijkstraPathFinder;
    private final MandatoryFieldValidator mandatoryFieldValidator;

    @Override
    protected Validator[] getValidators() {
        return new Validator[]{mandatoryFieldValidator};
    }

    @Override
    public void validate(GetShortestRouteRequest request) {
        if (request.getDepartureCityId().equals(request.getArrivalCityId())) {
            throw new InvalidParameterException(DEPARTURE_AND_ARRIVAL_CITIES_MUST_BE_DIFFERENT);
        }

        if (!cityService.existsById(request.getDepartureCityId())) {
            throw new NotFoundException(String.format(DEPARTURE_CITY_NOT_FOUND_WITH_ID, request.getDepartureCityId()));
        }

        if (!cityService.existsById(request.getArrivalCityId())) {
            throw new NotFoundException(String.format(ARRIVAL_CITY_NOT_FOUND_WITH_ID, request.getArrivalCityId()));
        }
    }

    @Override
    protected GetShortestRouteResponse executeBusinessLogic(GetShortestRouteRequest request) {
        List<RouteDto> routes = routeService.findAll();

        DijkstraResult dijkstraResult = dijkstraPathFinder.findLeastCostPath(
                routes,
                request.getDepartureCityId(),
                request.getArrivalCityId()
        );

        if (dijkstraResult == null) {
            throw new NotFoundException(NO_ROUTE_FOUND_BETWEEN_THE_SPECIFIED_CITIES);
        }

        List<CityDto> pathCities = dijkstraResult.getPathCityIds().stream()
                .map(cityId -> {
                    CityDto city = cityService.findById(cityId);
                    if (Objects.isNull(city)) {
                        throw new NotFoundException(String.format(CITY_NOT_FOUND, cityId));
                    }
                    return city;
                })
                .toList();

        return GetShortestRouteResponse.builder()
                .path(pathCities)
                .totalDistanceKm(dijkstraResult.getTotalDistanceKm())
                .totalTravelTimeMin(dijkstraResult.getTotalTravelTimeMin())
                .build();
    }
}
