package com.shipping.demo.controller.rest;

import com.shipping.demo.usecase.getcities.GetCitiesResponse;
import com.shipping.demo.usecase.getcities.GetCitiesUseCase;
import com.shipping.demo.usecase.getshortestroute.GetShortestRouteRequest;
import com.shipping.demo.usecase.getshortestroute.GetShortestRouteResponse;
import com.shipping.demo.usecase.getshortestroute.GetShortestRouteUseCase;
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
public class RouteController {
    public static final String ROUTE_API_PATH = "route";

    private final GetCitiesUseCase getCitiesUseCase;
    private final GetShortestRouteUseCase getShortestRouteUseCase;

    @GetMapping(value = "cities")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<GetCitiesResponse> getCities() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getCitiesUseCase.execute(null));
    }

    @GetMapping(value = "shortest")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<GetShortestRouteResponse> getShortestRoute(GetShortestRouteRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getShortestRouteUseCase.execute(request));
    }
}
