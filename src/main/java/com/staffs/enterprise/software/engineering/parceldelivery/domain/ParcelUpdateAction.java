package com.staffs.enterprise.software.engineering.parceldelivery.domain;

public class ParcelUpdateAction {
    private String parcelUuid;
    private UpdateActions action;
    private AppUser driver;

    private ParcelUpdateAction(String parcelUuid, UpdateActions action, AppUser driver) {
        this.parcelUuid = parcelUuid;
        this.action = action;
        this.driver = driver;
    }

    public String getParcelUuid() {
        return parcelUuid;
    }

    public UpdateActions getAction() {
        return action;
    }

    public AppUser getDriver() {
        return driver;
    }

    public static ParcelUpdateAction create(String parcelUuid, UpdateActions action, AppUser driver) {
        return new ParcelUpdateAction(parcelUuid, action, driver);
    }
}
