package com.shipping.demo.usecase.getparcelsevents;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class GetParcelsEventsResponse {

    private List<?> events;
}
