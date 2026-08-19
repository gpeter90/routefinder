package com.shipping.demo.domain.parcel;

import com.shipping.demo.common.domain.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelService extends DomainService<ParcelDto> {
    private final ParcelRepository parcelRepository;

    public List<ParcelDto> findAll() {
        return parcelRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .toList();
    }

    public List<ParcelDto> findBySenderId(Long senderId) {
        return parcelRepository.findBySenderId(senderId).stream()
                .map(this::mapEntityToDto)
                .toList();
    }

    public List<ParcelDto> findByReceiverId(Long receiverId) {
        return parcelRepository.findByReceiverId(receiverId).stream()
                .map(this::mapEntityToDto)
                .toList();
    }

    @Override
    protected void update(ParcelDto parcelDto) {
        parcelRepository.save(mapDtoToEntity(parcelDto));
    }

    private ParcelDto mapEntityToDto(Parcel parcel) {
        return ParcelDto.builder()
                .senderId(parcel.getSenderId())
                .receiverId(parcel.getReceiverId())
                .parcelNo(parcel.getParcelNo())
                .build();
    }

    private Parcel mapDtoToEntity(ParcelDto parcelDto) {
        return Parcel.builder()
                .senderId(parcelDto.getSenderId())
                .receiverId(parcelDto.getReceiverId())
                .parcelNo(parcelDto.getParcelNo())
                .build();
    }
}
