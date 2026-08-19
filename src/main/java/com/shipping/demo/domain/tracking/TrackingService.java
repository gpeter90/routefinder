package com.shipping.demo.domain.tracking;

import com.shipping.demo.common.domain.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingService extends DomainService<TrackingDto> {
    private final TrackingRepository trackingRepository;

    public List<TrackingDto> findByParcelId(Long parcelId) {
        return trackingRepository.findByParcelId(parcelId).stream()
                .map(this::mapEntityToDto)
                .toList();
    }

    @Override
    protected void update(TrackingDto trackingDto) {
        trackingRepository.save(mapDtoToEntity(trackingDto));
    }

    private TrackingDto mapEntityToDto(Tracking tracking) {
        return TrackingDto.builder()
                .parcelId(tracking.getParcelId())
                .eventDate(tracking.getEventDate())
                .eventId(tracking.getEventId())
                .build();
    }

    private Tracking mapDtoToEntity(TrackingDto trackingDto) {
        return Tracking.builder()
                .parcelId(trackingDto.getParcelId())
                .eventDate(trackingDto.getEventDate())
                .eventId(trackingDto.getEventId())
                .build();
    }
}
