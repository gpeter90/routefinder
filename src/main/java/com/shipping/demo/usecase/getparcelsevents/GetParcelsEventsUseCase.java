package com.shipping.demo.usecase.getparcelsevents;

import com.shipping.demo.common.usecase.UseCase;
import com.shipping.demo.common.validator.MandatoryFieldValidator;
import com.shipping.demo.common.validator.Validator;
import com.shipping.demo.domain.tracking.TrackingDto;
import com.shipping.demo.domain.tracking.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetParcelsEventsUseCase extends UseCase<GetParcelsEventsRequest, GetParcelsEventsResponse> {

    private final TrackingService trackingService;

    @Override
    protected Validator[] getValidators() {
        return new Validator[]{new MandatoryFieldValidator()};
    }

    @Override
    protected GetParcelsEventsResponse executeBusinessLogic(GetParcelsEventsRequest request) {
        List<TrackingDto> parcelsEvents = trackingService.findByParcelId(request.getParcelId());

        return GetParcelsEventsResponse.builder()
                .events(parcelsEvents)
                .build();
    }
}
