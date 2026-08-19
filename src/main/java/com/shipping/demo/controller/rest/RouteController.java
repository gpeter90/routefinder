package com.shipping.demo.controller.rest;

import com.shipping.demo.common.rest.ErrorResponse;
import com.shipping.demo.usecase.getcities.GetCitiesResponse;
import com.shipping.demo.usecase.getcities.GetCitiesUseCase;
import com.shipping.demo.usecase.getshortestroute.GetShortestRouteRequest;
import com.shipping.demo.usecase.getshortestroute.GetShortestRouteResponse;
import com.shipping.demo.usecase.getshortestroute.GetShortestRouteUseCase;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(RouteController.ROUTE_API_PATH)
@Tag(name = "Route Finder", description = "Airline route management and shortest path calculation using Dijkstra algorithm")
public class RouteController {
    public static final String ROUTE_API_PATH = "route";

    private final GetCitiesUseCase getCitiesUseCase;
    private final GetShortestRouteUseCase getShortestRouteUseCase;

    @GetMapping(value = "cities")
    @ResponseStatus(HttpStatus.OK)
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

    @GetMapping(value = "shortest")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Find shortest route",
            description = "Finds the shortest route between two cities using Dijkstra algorithm. "
                    + "Returns the path, total distance in km and total cost in EUR."
    )
    @ApiResponse(responseCode = "200", description = "Shortest route found successfully")
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
