package com.shipping.demo.usecase.getparcelsbysender;

import com.shipping.demo.common.validator.MandatoryField;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class GetParcelsBySenderRequest {

    @MandatoryField
    private Long senderId;
}
