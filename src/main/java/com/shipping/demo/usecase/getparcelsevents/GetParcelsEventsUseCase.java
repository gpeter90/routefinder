package com.shipping.demo.usecase.getparcelsevents;

import com.shipping.demo.common.usecase.UseCase;
import com.shipping.demo.common.validator.MandatoryFieldValidator;
import com.shipping.demo.common.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetParcelsEventsUseCase extends UseCase<GetParcelsEventsRequest, GetParcelsEventsResponse> {

    @Override
    protected Validator[] getValidators() {
        return new Validator[]{new MandatoryFieldValidator()};
    }

    @Override
    protected GetParcelsEventsResponse executeBusinessLogic(GetParcelsEventsRequest request) {
        return null;
    }
}
