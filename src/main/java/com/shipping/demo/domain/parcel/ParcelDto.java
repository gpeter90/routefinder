package com.shipping.demo.domain.parcel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class ParcelDto {
    private Long senderId;
    private Long receiverId;
    private String parcelNo;
}
