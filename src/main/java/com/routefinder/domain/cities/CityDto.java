package com.routefinder.domain.cities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class CityDto {
    private Long id;
    private String cityName;
    private String country;
    private String continent;
}
