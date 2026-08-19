package com.shipping.demo.usecase.getcities;

import com.shipping.demo.common.usecase.UseCase;
import com.shipping.demo.domain.city.CityDto;
import com.shipping.demo.domain.city.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCitiesUseCase extends UseCase<Void, GetCitiesResponse> {

    private final CityService cityService;

    @Override
    protected GetCitiesResponse executeBusinessLogic(Void request) {
        List<CityDto> cities = cityService.findAll();

        return GetCitiesResponse.builder()
                .cities(cities)
                .build();
    }
}
