package com.shipping.demo.domain.parcel;

import com.shipping.demo.common.domain.DomainService;
import com.shipping.demo.domain.client.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParcelService extends DomainService<ParcelDto> {
    private final ParcelRepository parcelRepository;

    @Override
    protected void update(ParcelDto parcelDto) {
        parcelRepository.save(mapDtoToEntity(parcelDto));
    }

    private Parcel mapDtoToEntity(ParcelDto parcelDto) {
        return Parcel.builder()
                .sender(Client.builder().id(parcelDto.getSenderId()).build())
                .receiver(Client.builder().id(parcelDto.getReceiverId()).build())
                .parcelNo(parcelDto.getParcelNo())
                .build();
    }
}
