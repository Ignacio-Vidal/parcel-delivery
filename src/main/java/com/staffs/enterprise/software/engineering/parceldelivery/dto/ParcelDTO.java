package com.staffs.enterprise.software.engineering.parceldelivery.dto;

import java.util.Objects;

public class ParcelDTO {
    private String uuid;
    private String pickupAddress;
    private String destinationAddress;
    private String status;

    public ParcelDTO(String uuid, String pickupAddress, String destinationAddress, String status) {
        this.uuid = uuid;
        this.pickupAddress = pickupAddress;
        this.destinationAddress = destinationAddress;
        this.status = status;
    }

    public ParcelDTO() {
    }

    public String getUuid() {
        return uuid;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getStatus() {
        return status;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ParcelDTO{" +
                "uuid='" + uuid + '\'' +
                ", pickupAddress='" + pickupAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParcelDTO parcelDTO = (ParcelDTO) o;

        if (!uuid.equals(parcelDTO.uuid)) return false;
        if (!pickupAddress.equals(parcelDTO.pickupAddress)) return false;
        if (!destinationAddress.equals(parcelDTO.destinationAddress)) return false;
        return status.equals(parcelDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
