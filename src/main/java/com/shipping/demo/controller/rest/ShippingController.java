package com.shipping.demo.controller.rest;

import com.shipping.demo.common.rest.ErrorResponse;
import com.shipping.demo.usecase.getclients.GetClientsResponse;
import com.shipping.demo.usecase.getclients.GetClientsUseCase;
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
@RequestMapping(ShippingController.SHIPPING_API_PATH)
@Tag(name = "Parcel Tracking", description = "Client and parcel management with event tracking")
public class ShippingController {
    public static final String SHIPPING_API_PATH = "shipping";

    private final GetClientsUseCase getClientsUseCase;
    private final GetParcelsUseCase getParcelsUseCase;
    private final GetParcelsBySenderUseCase getParcelsBySenderUseCase;
    private final GetParcelsByAddresseeUseCase getParcelsByAddresseeUseCase;
    private final GetParcelsEventsUseCase getParcelsEventsUseCase;

    @GetMapping(value = "clients")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all clients",
            description = "Returns the list of all registered clients"
    )
    @ApiResponse(responseCode = "200", description = "Clients retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    ResponseEntity<GetClientsResponse> getClients() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getClientsUseCase.execute(null));
    }

    @GetMapping(value = "parcels")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all parcels",
            description = "Returns the list of all parcels in the system"
    )
    @ApiResponse(responseCode = "200", description = "Parcels retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    ResponseEntity<GetParcelsResponse> getParcels() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getParcelsUseCase.execute(null));
    }

    @GetMapping(value = "parcels/sender")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get parcels by sender",
            description = "Returns all parcels sent by the specified client"
    )
    @ApiResponse(responseCode = "200", description = "Parcels retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request - senderId is required",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    ResponseEntity<GetParcelsBySenderResponse> getParcelsBySender(GetParcelsBySenderRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getParcelsBySenderUseCase.execute(request));
    }

    @GetMapping(value = "parcels/addressee")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get parcels by addressee",
            description = "Returns all parcels addressed to the specified client"
    )
    @ApiResponse(responseCode = "200", description = "Parcels retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request - addresseeId is required",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    ResponseEntity<GetParcelsByAddresseeResponse> getParcelsByAddressee(GetParcelsByAddresseeRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getParcelsByAddresseeUseCase.execute(request));
    }

    @GetMapping(value = "parcels/events")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get events for a parcel",
            description = "Returns the tracking event history for the specified parcel"
    )
    @ApiResponse(responseCode = "200", description = "Events retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request - parcelId is required",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    ResponseEntity<GetParcelsEventsResponse> getParcelsEvents(GetParcelsEventsRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getParcelsEventsUseCase.execute(request));
    }
}
