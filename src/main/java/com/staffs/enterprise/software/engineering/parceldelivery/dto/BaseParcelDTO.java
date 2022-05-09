package com.staffs.enterprise.software.engineering.parceldelivery.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class BaseParcelDTO {
    @NotBlank
    private String pickupAddress;
    @NotBlank
    private String destinationAddress;
    @NotBlank
    private String status;

    public BaseParcelDTO(String pickupAddress, String destinationAddress, String status) {

        this.pickupAddress = pickupAddress;
        this.destinationAddress = destinationAddress;
        this.status = status;
    }

    public BaseParcelDTO() {
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
                ", pickupAddress='" + pickupAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseParcelDTO parcelDTO = (BaseParcelDTO) o;

        if (!pickupAddress.equals(parcelDTO.pickupAddress)) return false;
        if (!destinationAddress.equals(parcelDTO.destinationAddress)) return false;
        return status.equals(parcelDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickupAddress, destinationAddress, status);
    }
}
