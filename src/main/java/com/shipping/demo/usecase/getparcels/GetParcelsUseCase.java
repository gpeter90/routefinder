package com.shipping.demo.usecase.getparcels;

import com.shipping.demo.common.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetParcelsUseCase extends UseCase<GetParcelsRequest, GetParcelsResponse> {

    @Override
    protected GetParcelsResponse executeBusinessLogic(GetParcelsRequest request) {
        return null;
    }
}
