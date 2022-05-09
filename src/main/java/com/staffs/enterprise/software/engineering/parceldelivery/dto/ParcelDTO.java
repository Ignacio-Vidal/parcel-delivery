package com.staffs.enterprise.software.engineering.parceldelivery.dto;

import java.util.Objects;

public class ParcelDTO extends BaseParcelDTO {
    private String uuid;

    public ParcelDTO(String uuid, String pickupAddress, String destinationAddress, String status) {
        super(pickupAddress, destinationAddress, status);
        this.uuid = uuid;
    }

    public ParcelDTO() {
    }

    public String getUuid() {
        return uuid;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ParcelDTO{" +
                "uuid='" + uuid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParcelDTO parcelDTO = (ParcelDTO) o;
        return  (!uuid.equals(parcelDTO.uuid));
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
