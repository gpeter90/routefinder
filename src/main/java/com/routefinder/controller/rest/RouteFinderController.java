package com.routefinder.controller.rest;

import com.routefinder.common.rest.ErrorResponse;
import com.routefinder.usecase.getcities.GetCitiesResponse;
import com.routefinder.usecase.getcities.GetCitiesUseCase;
import com.routefinder.usecase.getroutes.GetRoutesResponse;
import com.routefinder.usecase.getroutes.GetRoutesUseCase;
import com.routefinder.usecase.getshortestroute.GetShortestRouteRequest;
import com.routefinder.usecase.getshortestroute.GetShortestRouteResponse;
import com.routefinder.usecase.getshortestroute.GetShortestRouteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(RouteFinderController.ROUTE_FINDER_API_PATH)
@Tag(name = "Route Finder", description = "Airline route management and least cost path calculation using Dijkstra algorithm")
public class RouteFinderController {
    public static final String ROUTE_FINDER_API_PATH = "/routefinder";

    private final GetCitiesUseCase getCitiesUseCase;
    private final GetRoutesUseCase getRoutesUseCase;
    private final GetShortestRouteUseCase getShortestRouteUseCase;

    @GetMapping(value = "cities")
    @Operation(
            summary = "Get all cities",
            description = "Returns the list of all available cities in Europe and America"
    )
    @ApiResponse(responseCode = "200", description = "Cities retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    ResponseEntity<GetCitiesResponse> getCities() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getCitiesUseCase.execute(null));
    }

    @GetMapping(value = "routes")
    @Operation(
            summary = "Get all routes",
            description = "Returns all available routes with departure city, arrival city, distance in km "
                    + "and travel time in minutes (cost)"
    )
    @ApiResponse(responseCode = "200", description = "Routes retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    ResponseEntity<GetRoutesResponse> getRoutes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getRoutesUseCase.execute(null));
    }

    @GetMapping(value = "shortest")
    @Operation(
            summary = "Find least cost route",
            description = "Finds the least cost route between two cities using Dijkstra algorithm. "
                    + "Cost is measured in travel time (minutes). Returns the path, total distance in km "
                    + "and total travel time in minutes."
    )
    @ApiResponse(responseCode = "200", description = "Least cost route found successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request - missing or invalid city IDs",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "No route found between the specified cities",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    ResponseEntity<GetShortestRouteResponse> getShortestRoute(GetShortestRouteRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getShortestRouteUseCase.execute(request));
    }
}
