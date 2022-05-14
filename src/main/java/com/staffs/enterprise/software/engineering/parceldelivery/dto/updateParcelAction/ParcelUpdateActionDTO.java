package com.staffs.enterprise.software.engineering.parceldelivery.dto.updateParcelAction;

import com.staffs.enterprise.software.engineering.parceldelivery.dto.parcel.BaseParcelDTO;

import java.util.Objects;

public class ParcelUpdateActionDTO extends BaseUpdateParcelAction {
    private String parcelUuid;
    private String driver;

    public ParcelUpdateActionDTO() {
    }

    public ParcelUpdateActionDTO(String action, String parcelUuid, String driver) {
        super(action, parcelUuid);
        this.parcelUuid = parcelUuid;
        this.driver = driver;
    }

    public String getParcelUuid() {
        return parcelUuid;
    }

    public void setParcelUuid(String parcelUuid) {
        this.parcelUuid = parcelUuid;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ParcelUpdateActionDTO that = (ParcelUpdateActionDTO) o;
        return parcelUuid.equals(that.parcelUuid) && driver.equals(that.driver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parcelUuid, driver);
    }
}
