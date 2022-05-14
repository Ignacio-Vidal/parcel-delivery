package com.staffs.enterprise.software.engineering.parceldelivery.dto.updateParcelAction;

import java.util.Objects;

public class BaseUpdateParcelAction {
    private String action;
    private String parcelUuid;

    public BaseUpdateParcelAction() {
    }

    public BaseUpdateParcelAction(String action, String parcelUuid) {
        this.action = action;
        this.parcelUuid = parcelUuid;
    }


    public String getParcelUuid() {
        return parcelUuid;
    }

    public void setParcelUuid(String parcelUuid) {
        this.parcelUuid = parcelUuid;
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
        if (!(o instanceof BaseUpdateParcelAction)) return false;
        BaseUpdateParcelAction that = (BaseUpdateParcelAction) o;
        return parcelUuid.equals(that.parcelUuid) && action.equals(that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parcelUuid, action);
    }
}
