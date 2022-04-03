package com.staffs.enterprise.software.engineering.parceldelivery.domain;

import io.swagger.client.model.ParcelStatus;

import java.util.UUID;

public class Parcel {
    private String uuid;
    private String pickupAddress;
    private String destinationAddress;
    private String issuer;
    private ParcelStatus status;

    private Parcel(String uuid, String pickupAddress, String destinationAddress, String issuer, ParcelStatus status) {
        this.uuid = uuid;
        this.pickupAddress = pickupAddress;
        this.destinationAddress = destinationAddress;
        this.issuer = issuer;
        this.status = status;
    }

    public static Parcel create(String uuid, String pickupAddress, String destinationAddress, String issuer, ParcelStatus status) {
        return new Parcel(uuid, pickupAddress, destinationAddress, issuer, status);
    }

    public static Parcel create( String pickupAddress, String destinationAddress, String issuer, ParcelStatus status) {
        return new Parcel(UUID.randomUUID().toString(), pickupAddress, destinationAddress, issuer, status);
    }

    public String getUuid() {
        return uuid;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getIssuer() {
        return issuer;
    }

    public ParcelStatus getStatus() {

        return status;
    }
}
