package com.staffs.enterprise.software.engineering.parceldelivery.dto;

import java.util.Objects;

public class UpdateParcelDTO extends BaseParcelDTO {
    private String parcelUuid;
    private String userUuid;

    public UpdateParcelDTO() {
    }

    public UpdateParcelDTO(String pickupAddress, String destinationAddress, String status, String parcelUuid, String userUuid, String targetStatus) {
        super(pickupAddress, destinationAddress, status);
        this.parcelUuid = parcelUuid;
        this.userUuid = userUuid;
    }

    public String getParcelUuid() {
        return parcelUuid;
    }

    public void setParcelUuid(String parcelUuid) {
        this.parcelUuid = parcelUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdateParcelDTO that = (UpdateParcelDTO) o;
        return parcelUuid.equals(that.parcelUuid) && userUuid.equals(that.userUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parcelUuid, userUuid);
    }
}
