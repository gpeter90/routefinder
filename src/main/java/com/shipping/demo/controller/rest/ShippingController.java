package com.shipping.demo.controller.rest;

import com.shipping.demo.usecase.getclients.GetClientsRequest;
import com.shipping.demo.usecase.getclients.GetClientsResponse;
import com.shipping.demo.usecase.getclients.GetClientsUseCase;
import com.shipping.demo.usecase.getparcels.GetParcelsRequest;
import com.shipping.demo.usecase.getparcels.GetParcelsResponse;
import com.shipping.demo.usecase.getparcels.GetParcelsUseCase;
import com.shipping.demo.usecase.getparcelsbyaddress.GetParcelsByAddresseeRequest;
import com.shipping.demo.usecase.getparcelsbyaddress.GetParcelsByAddresseeResponse;
import com.shipping.demo.usecase.getparcelsbyaddress.GetParcelsByAddresseeUseCase;
import com.shipping.demo.usecase.getparcelsbysender.GetParcelsBySenderRequest;
import com.shipping.demo.usecase.getparcelsbysender.GetParcelsBySenderResponse;
import com.shipping.demo.usecase.getparcelsbysender.GetParcelsBySenderUseCase;
import com.shipping.demo.usecase.getparcelsevents.GetParcelsEventsRequest;
import com.shipping.demo.usecase.getparcelsevents.GetParcelsEventsResponse;
import com.shipping.demo.usecase.getparcelsevents.GetParcelsEventsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(ShippingController.SHIPPING_API_PATH)
public class ShippingController {
    public static final String SHIPPING_API_PATH = "shipping";

    private final GetClientsUseCase getClientsUseCase;
    private final GetParcelsUseCase getParcelsUseCase;
    private final GetParcelsBySenderUseCase getParcelsBySenderUseCase;
    private final GetParcelsByAddresseeUseCase getParcelsByAddresseeUseCase;
    private final GetParcelsEventsUseCase getParcelsEventsUseCase;

    @GetMapping(value = "clients")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<GetClientsResponse> getClients(
            @RequestBody GetClientsRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getClientsUseCase.execute(request));
    }

    @GetMapping(value = "parcels")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<GetParcelsResponse> getParcels(
            @RequestBody GetParcelsRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getParcelsUseCase.execute(request));
    }

    @GetMapping(value = "parcels/sender")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<GetParcelsBySenderResponse> getParcelsBySender(
            @RequestBody GetParcelsBySenderRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getParcelsBySenderUseCase.execute(request));
    }

    @GetMapping(value = "parcels/addressee")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<GetParcelsByAddresseeResponse> getParcelsByAddressee(
            @RequestBody GetParcelsByAddresseeRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getParcelsByAddresseeUseCase.execute(request));
    }

    @GetMapping(value = "parcels/events")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<GetParcelsEventsResponse> getParcelsEvents(
            @RequestBody GetParcelsEventsRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getParcelsEventsUseCase.execute(request));
    }
}
