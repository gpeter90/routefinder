package com.shipping.demo.usecase.getparcelsbyaddress;

import com.shipping.demo.common.usecase.UseCase;
import com.shipping.demo.common.validator.MandatoryFieldValidator;
import com.shipping.demo.common.validator.Validator;
import com.shipping.demo.domain.parcel.ParcelDto;
import com.shipping.demo.domain.parcel.ParcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetParcelsByAddresseeUseCase extends UseCase<GetParcelsByAddresseeRequest, GetParcelsByAddresseeResponse> {

    private final ParcelService parcelService;

    @Override
    protected Validator[] getValidators() {
        return new Validator[]{new MandatoryFieldValidator()};
    }

    @Override
    protected GetParcelsByAddresseeResponse executeBusinessLogic(GetParcelsByAddresseeRequest request) {
        List<ParcelDto> parcelsByReceiver = parcelService.findByReceiverId(request.getAddresseeId());

        return GetParcelsByAddresseeResponse.builder()
                .parcels(parcelsByReceiver)
                .build();
    }
}
