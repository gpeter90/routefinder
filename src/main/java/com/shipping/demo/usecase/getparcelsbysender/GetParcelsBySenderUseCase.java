package com.shipping.demo.usecase.getparcelsbysender;

import com.shipping.demo.common.usecase.UseCase;
import com.shipping.demo.common.validator.MandatoryFieldValidator;
import com.shipping.demo.common.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetParcelsBySenderUseCase extends UseCase<GetParcelsBySenderRequest, GetParcelsBySenderResponse> {

    @Override
    protected Validator[] getValidators() {
        return new Validator[]{new MandatoryFieldValidator()};
    }

    @Override
    protected GetParcelsBySenderResponse executeBusinessLogic(GetParcelsBySenderRequest request) {
        return null;
    }
}
