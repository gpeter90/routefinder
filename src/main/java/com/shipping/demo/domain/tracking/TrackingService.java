package com.shipping.demo.domain.tracking;

import com.shipping.demo.common.domain.DomainService;
import com.shipping.demo.domain.event.Event;
import com.shipping.demo.domain.parcel.Parcel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackingService extends DomainService<TrackingDto> {
    private final TrackingRepository trackingRepository;

    @Override
    protected void update(TrackingDto trackingDto) {
        trackingRepository.save(mapDtoToEntity(trackingDto));
    }

    private Tracking mapDtoToEntity(TrackingDto trackingDto) {
        return Tracking.builder()
                .parcel(Parcel.builder().id(Long.valueOf(trackingDto.getParcelId())).build())
                .eventDate(trackingDto.getEventDate())
                .event(Event.builder().id(trackingDto.getEventId()).build())
                .build();
    }
}
