package com.shipping.demo.domain.tracking;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@RequiredArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class TrackingDto {
    private Integer parcelId;
    private LocalDateTime eventDate;
    private Short eventId;
}
