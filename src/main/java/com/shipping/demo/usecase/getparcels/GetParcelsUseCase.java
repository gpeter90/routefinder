package com.shipping.demo.usecase.getparcels;

import com.shipping.demo.common.usecase.UseCase;
import com.shipping.demo.domain.parcel.ParcelDto;
import com.shipping.demo.domain.parcel.ParcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetParcelsUseCase extends UseCase<Void, GetParcelsResponse> {

    private final ParcelService parcelService;

    @Override
    protected GetParcelsResponse executeBusinessLogic(Void request) {
        List<ParcelDto> parcels = parcelService.findAll();

        return GetParcelsResponse.builder()
                .parcels(parcels)
                .build();
    }
}
