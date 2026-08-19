package com.shipping.demo.usecase.getclients;

import com.shipping.demo.common.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetClientsUseCase extends UseCase<GetClientsRequest, GetClientsResponse> {

    @Override
    protected GetClientsResponse executeBusinessLogic(GetClientsRequest request) {
        return null;
    }
}
