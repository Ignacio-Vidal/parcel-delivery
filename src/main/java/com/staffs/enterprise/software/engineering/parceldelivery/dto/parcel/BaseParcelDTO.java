package com.staffs.enterprise.software.engineering.parceldelivery.dto.parcel;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class BaseParcelDTO {
    @NotBlank
    private String pickupAddress;
    @NotBlank
    private String destinationAddress;
    @NotBlank
    private String recipientName;

    public BaseParcelDTO(String pickupAddress, String destinationAddress, String recipientName) {
        this.pickupAddress = pickupAddress;
        this.destinationAddress = destinationAddress;
        this.recipientName = recipientName;
    }

    public BaseParcelDTO() {
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }


    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }


    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    @Override
    public String toString() {
        return "ParcelDTO{" +
                ", pickupAddress='" + pickupAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", recipient_name='" + recipientName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseParcelDTO that = (BaseParcelDTO) o;

        if (!pickupAddress.equals(that.pickupAddress)) return false;
        if (!destinationAddress.equals(that.destinationAddress)) return false;
        return recipientName.equals(that.recipientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickupAddress, destinationAddress, recipientName);
    }
}
