package com.staffs.enterprise.software.engineering.parceldelivery.mapper;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.ParcelStatus;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.CreateParcelDTO;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.ParcelDTO;
import org.springframework.stereotype.Component;

@Component
public class ParcelMapper {
    public ParcelDTO toDTO(Parcel parcel) {
        ParcelDTO dto = new ParcelDTO();
        dto.setDestinationAddress(parcel.getDestinationAddress());
        dto.setPickupAddress(parcel.getPickupAddress());
        dto.setUuid(parcel.getUuid());
        dto.setStatus(parcel.getStatus().toString());
        return dto;
    }

    public Parcel toParcel(ParcelDTO dto, AppUser user) {
        Parcel.Builder builder = new Parcel.Builder();
        builder.withUser(user)
                .withPickupAddress(dto.getPickupAddress())
                .withDestinationAddress(dto.getDestinationAddress())
                .withStatus(ParcelStatus.valueOf(dto.getStatus()))
                .withUuid(dto.getUuid());
        return builder.build();
    }

    public Parcel toParcel(CreateParcelDTO dto, AppUser user) {
        Parcel.Builder builder = new Parcel.Builder();
        builder.withUser(user)
                .withPickupAddress(dto.getPickupAddress())
                .withDestinationAddress(dto.getDestinationAddress())
                .withStatus(ParcelStatus.valueOf(dto.getStatus()));
        return builder.build();
    }
}