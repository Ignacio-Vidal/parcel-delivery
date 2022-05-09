package com.staffs.enterprise.software.engineering.parceldelivery.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class BaseUpdateParcelAction {
    private String parcelUuid;
    @NotBlank
    private String status;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParcelUuid() {
        return parcelUuid;
    }

    public void setParcelUuid(String parcelUuid) {
        this.parcelUuid = parcelUuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseUpdateParcelAction)) return false;
        BaseUpdateParcelAction that = (BaseUpdateParcelAction) o;
        return parcelUuid.equals(that.parcelUuid) && status.equals(that.status) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parcelUuid, status, email);
    }
}
