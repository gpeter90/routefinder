package com.shipping.demo.usecase.getshortestroute;

import com.shipping.demo.common.validator.MandatoryField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GetShortestRouteRequest {

    @MandatoryField
    private Long departureCityId;

    @MandatoryField
    private Long arrivalCityId;
}
