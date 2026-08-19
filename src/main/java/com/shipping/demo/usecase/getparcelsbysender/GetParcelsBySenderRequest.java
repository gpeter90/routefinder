package com.shipping.demo.usecase.getparcelsbysender;

import com.shipping.demo.common.validator.MandatoryField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GetParcelsBySenderRequest {

    @MandatoryField
    private Long senderId;
}
