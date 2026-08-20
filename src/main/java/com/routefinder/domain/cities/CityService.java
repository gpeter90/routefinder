package com.routefinder.domain.cities;

import com.routefinder.common.domain.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService extends DomainService<CityDto> {
    private final CityRepository cityRepository;

    public List<CityDto> findAll() {
        return cityRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .toList();
    }

    public boolean existsById(Long cityId) {
        return cityRepository.existsById(cityId);
    }

    public CityDto findById(Long cityId) {
        return cityRepository.findById(cityId)
                .map(this::mapEntityToDto)
                .orElse(null);
    }

    @Override
    protected void update(CityDto cityDto) {
        cityRepository.save(mapDtoToEntity(cityDto));
    }

    private CityDto mapEntityToDto(City city) {
        return CityDto.builder()
                .id(city.getId())
                .cityName(city.getCityName())
                .country(city.getCountry())
                .continent(city.getContinent())
                .build();
    }

    private City mapDtoToEntity(CityDto cityDto) {
        return City.builder()
                .cityName(cityDto.getCityName())
                .country(cityDto.getCountry())
                .continent(cityDto.getContinent())
                .build();
    }
}
