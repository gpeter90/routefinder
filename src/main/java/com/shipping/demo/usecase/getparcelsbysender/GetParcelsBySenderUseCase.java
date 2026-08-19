package com.shipping.demo.usecase.getparcelsbysender;

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
public class GetParcelsBySenderUseCase extends UseCase<GetParcelsBySenderRequest, GetParcelsBySenderResponse> {

    private final ParcelService parcelService;

    @Override
    protected Validator[] getValidators() {
        return new Validator[]{new MandatoryFieldValidator()};
    }

    @Override
    protected GetParcelsBySenderResponse executeBusinessLogic(GetParcelsBySenderRequest request) {
        List<ParcelDto> parcelsBySender = parcelService.findBySenderId(request.getSenderId());

        return GetParcelsBySenderResponse.builder()
                .parcels(parcelsBySender)
                .build();
    }
}
