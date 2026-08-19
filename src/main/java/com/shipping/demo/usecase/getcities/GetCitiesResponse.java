package com.shipping.demo.usecase.getcities;

import com.shipping.demo.domain.cities.CityDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class GetCitiesResponse {

    private List<CityDto> cities;
}
