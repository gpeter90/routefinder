package com.shipping.demo.usecase.getparcelsevents;

import com.shipping.demo.common.validator.MandatoryField;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class GetParcelsEventsRequest {

    @MandatoryField
    private Integer parcelId;
}
