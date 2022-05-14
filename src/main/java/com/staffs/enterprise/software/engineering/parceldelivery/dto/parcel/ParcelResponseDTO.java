package com.staffs.enterprise.software.engineering.parceldelivery.dto.parcel;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ParcelResponseDTO extends BaseParcelDTO {
    private String uuid;
    private String status;
    private String owner;
    private String driver;

    public ParcelResponseDTO(String owner, String pickupAddress, String destinationAddress, String status, String uuid) {
        super(pickupAddress, destinationAddress, status);
        this.owner = owner;
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
        ParcelResponseDTO that = (ParcelResponseDTO) o;
        return !owner.equals(that.owner) &&
                !status.equals(that.status) &&
                !uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner);
    }
}
