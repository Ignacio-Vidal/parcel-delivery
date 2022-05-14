package com.staffs.enterprise.software.engineering.parceldelivery.mapper;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.ParcelStatus;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.parcel.ParcelResponseDTO;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.parcel.RegisterParcelDTO;
import org.springframework.stereotype.Component;

@Component
public class ParcelMapper {
    public ParcelResponseDTO toDTO(Parcel parcel) {
        ParcelResponseDTO dto = new ParcelResponseDTO();
        dto.setDestinationAddress(parcel.getDestinationAddress());
        dto.setPickupAddress(parcel.getPickupAddress());
        dto.setOwner(parcel.getUuid());
        dto.setStatus(parcel.getStatus().toString());
        dto.setUuid(parcel.getUuid());
        dto.setRecipientName(parcel.getRecipientName());
        return dto;
    }

    public Parcel toParcel(RegisterParcelDTO dto, AppUser user) {
        Parcel.Builder builder = new Parcel.Builder();
        builder.withOwner(user)
                .withPickupAddress(dto.getPickupAddress())
                .withDestinationAddress(dto.getDestinationAddress())
                .withStatus(ParcelStatus.REGISTERED)
                .withRecipientName(dto.getRecipientName());
        return builder.build();
    }
}
