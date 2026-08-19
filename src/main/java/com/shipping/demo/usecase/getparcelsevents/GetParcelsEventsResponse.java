package com.shipping.demo.usecase.getparcelsevents;

import com.shipping.demo.domain.tracking.TrackingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class GetParcelsEventsResponse {

    private List<TrackingDto> events;
}
