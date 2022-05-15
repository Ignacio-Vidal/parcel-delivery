package com.staffs.enterprise.software.engineering.parceldelivery.dto.updateParcelAction;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ParcelUpdateActionDTO {
    @NotBlank
    private String action;

    public ParcelUpdateActionDTO() {
    }

    public ParcelUpdateActionDTO(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParcelUpdateActionDTO)) return false;
        ParcelUpdateActionDTO that = (ParcelUpdateActionDTO) o;
        return action.equals(that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(action);
    }
}
