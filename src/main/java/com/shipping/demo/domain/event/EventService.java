package com.shipping.demo.domain.event;

import com.shipping.demo.common.domain.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService extends DomainService<EventDto> {
    private final EventRepository eventRepository;

    @Override
    protected void update(EventDto eventDto) {
        eventRepository.save(mapDtoToEntity(eventDto));
    }

    private Event mapDtoToEntity(EventDto eventDto) {
        return Event.builder()
                .eventName(eventDto.getEventName())
                .build();
    }
}
