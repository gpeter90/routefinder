package com.routefinder.usecase.getcities;

import com.routefinder.domain.cities.CityDto;
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
