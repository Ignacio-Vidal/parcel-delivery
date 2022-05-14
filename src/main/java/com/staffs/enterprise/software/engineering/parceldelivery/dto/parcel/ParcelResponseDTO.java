package com.staffs.enterprise.software.engineering.parceldelivery.dto.parcel;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ParcelResponseDTO extends BaseParcelDTO {
    private String uuid;
    private String owner;
    private String recipientName;
    @NotBlank
    private String status;

    public ParcelResponseDTO(String owner, String pickupAddress, String destinationAddress, String status, String recipientName, String uuid) {
        super(pickupAddress, destinationAddress, status);
        this.owner = owner;
        this.recipientName = recipientName;
        this.uuid = uuid;
    }

    public ParcelResponseDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getOwner() {
        return owner;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "ParcelDTO{" +
                "uuid='" + owner + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParcelResponseDTO parcelResponseDTO = (ParcelResponseDTO) o;
        return (!owner.equals(parcelResponseDTO.owner));
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner);
    }
}
