package com.shipping.demo.usecase.getparcelsbyaddress;

import com.shipping.demo.common.validator.MandatoryField;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class GetParcelsByAddresseeRequest {

    @MandatoryField
    private Long addresseeId;
}
