package com.routefinder.usecase.getshortestroute;

import com.routefinder.domain.routes.RouteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DijkstraPathFinderTest {

    private DijkstraPathFinder dijkstraPathFinder;

    @BeforeEach
    void setUp() {
        dijkstraPathFinder = new DijkstraPathFinder();
    }

    @Test
    void shouldFindDirectRoute() {
        List<RouteDto> routes = List.of(
                buildRoute(1L, 2L, 500, 60)
        );

        DijkstraResult result = dijkstraPathFinder.findLeastCostPath(routes, 1L, 2L);

        assertNotNull(result);
        assertEquals(List.of(1L, 2L), result.getPathCityIds());
        assertEquals(500, result.getTotalDistanceKm());
        assertEquals(60, result.getTotalTravelTimeMin());
    }

    @Test
    void shouldFindMultiHopRoute() {
        List<RouteDto> routes = List.of(
                buildRoute(1L, 2L, 300, 40),
                buildRoute(2L, 3L, 400, 50),
                buildRoute(3L, 4L, 500, 60)
        );

        DijkstraResult result = dijkstraPathFinder.findLeastCostPath(routes, 1L, 4L);

        assertNotNull(result);
        assertEquals(List.of(1L, 2L, 3L, 4L), result.getPathCityIds());
        assertEquals(1200, result.getTotalDistanceKm());
        assertEquals(150, result.getTotalTravelTimeMin());
    }

    @Test
    void shouldReturnNullWhenNoRouteExists() {
        List<RouteDto> routes = List.of(
                buildRoute(1L, 2L, 300, 40),
                buildRoute(3L, 4L, 500, 60)
        );

        DijkstraResult result = dijkstraPathFinder.findLeastCostPath(routes, 1L, 4L);

        assertNull(result);
    }

    @Test
    void shouldChooseLeastCostPath() {
        List<RouteDto> routes = List.of(
                buildRoute(1L, 2L, 1000, 200),
                buildRoute(1L, 3L, 300, 40),
                buildRoute(3L, 2L, 400, 50)
        );

        DijkstraResult result = dijkstraPathFinder.findLeastCostPath(routes, 1L, 2L);

        assertNotNull(result);
        assertEquals(List.of(1L, 3L, 2L), result.getPathCityIds());
        assertEquals(700, result.getTotalDistanceKm());
        assertEquals(90, result.getTotalTravelTimeMin());
    }

    @Test
    void shouldHandleBidirectionalRoutes() {
        List<RouteDto> routes = List.of(
                buildRoute(1L, 2L, 500, 60),
                buildRoute(2L, 3L, 400, 50)
        );

        DijkstraResult result = dijkstraPathFinder.findLeastCostPath(routes, 3L, 1L);

        assertNotNull(result);
        assertEquals(List.of(3L, 2L, 1L), result.getPathCityIds());
        assertEquals(900, result.getTotalDistanceKm());
        assertEquals(110, result.getTotalTravelTimeMin());
    }

    @Test
    void shouldReturnNullWhenRoutesListIsEmpty() {
        DijkstraResult result = dijkstraPathFinder.findLeastCostPath(List.of(), 1L, 2L);

        assertNull(result);
    }

    private RouteDto buildRoute(Long departureCityId, Long arrivalCityId, int distanceKm, int travelTimeMin) {
        return RouteDto.builder()
                .departureCityId(departureCityId)
                .arrivalCityId(arrivalCityId)
                .distanceKm(distanceKm)
                .travelTimeMin(travelTimeMin)
                .build();
    }
}
