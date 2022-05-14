package com.staffs.enterprise.software.engineering.parceldelivery.domain;

import org.springframework.data.annotation.Id;

import java.util.Set;
import java.util.UUID;

public class Parcel {
    @Id
    private Long id;
    private String uuid;
    private String pickupAddress;
    private String destinationAddress;
    private ParcelStatus status;
    private AppUser owner;
    private AppUser driver;
    private String recipientName;

    private Parcel(Long id, String uuid, String pickupAddress, String destinationAddress, ParcelStatus status, AppUser owner, String recipientName, AppUser driver) {
        this.id = id;
        this.uuid = uuid;
        this.pickupAddress = pickupAddress;
        this.destinationAddress = destinationAddress;
        this.status = status;
        this.owner = owner;
        this.recipientName = recipientName;
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

    public ParcelStatus getStatus() {
        return status;
    }

    public AppUser getOwner() {
        return owner;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public AppUser getDriver(){
        return driver;
    }

    public void readyForAllocation() {
        Set<ParcelStatus> statuses = Set.of(ParcelStatus.REGISTERED, ParcelStatus.REJECTED_BY_CUSTOMER);
        if (!statuses.contains(this.status)) {
            throw new IllegalStateException("Parcel is not REGISTERED or REJECTED_BY_CUSTOMER");
        }
        status = ParcelStatus.READY_FOR_ALLOCATION;
    }

    public void assignDelivery(AppUser user) {
        if (!status.equals(ParcelStatus.READY_FOR_ALLOCATION) || this.owner != null) {
            throw new IllegalStateException("Parcel is not ready for allocation");
        }
        this.owner = user;
        this.status = ParcelStatus.DELIVERY_ASSIGNED;
    }

    public void outForDelivery() {
        if (!status.equals(ParcelStatus.DELIVERY_ASSIGNED)) {
            throw new IllegalStateException("Parcel is not assigned for delivery");
        }
        status = ParcelStatus.OUT_FOR_DELIVERY;
    }

    public void delivered() {
        if (!status.equals(ParcelStatus.OUT_FOR_DELIVERY)) {
            throw new IllegalStateException("Parcel is not out for delivery");
        }
        status = ParcelStatus.DELIVERED;
    }

    public void returned() {
        if (!status.equals(ParcelStatus.OUT_FOR_DELIVERY)) {
            throw new IllegalStateException("Parcel is not out for delivery");
        }
        status = ParcelStatus.READY_FOR_ALLOCATION;
        owner = null;
    }

    public void rejected() {
        if (!status.equals(ParcelStatus.OUT_FOR_DELIVERY)) {
            throw new IllegalStateException("Parcel is not out for delivery");
        }
        status = ParcelStatus.REJECTED_BY_CUSTOMER;
    }

    public static class Builder {
        private Long id;
        private String uuid;
        private String pickupAddress;
        private String destinationAddress;
        private ParcelStatus status;
        private AppUser owner;
        private AppUser driver;
        private String recipientName;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder withUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder withPickupAddress(String pickupAddress) {
            this.pickupAddress = pickupAddress;
            return this;
        }

        public Builder withDestinationAddress(String destinationAddress) {
            this.destinationAddress = destinationAddress;
            return this;
        }

        public Builder withStatus(ParcelStatus status) {
            this.status = status;
            return this;
        }

        public Builder withOwner(AppUser owner) {
            this.owner = owner;
            return this;
        }

        public Builder withRecipientName(String recipientName) {
            this.recipientName = recipientName;
            return this;
        }

        public Builder withDriver(AppUser driver) {
            this.driver = driver;
            return this;
        }

        public Parcel build() {
            if (uuid == null) {
                this.uuid = UUID.randomUUID().toString();
            }
            return new Parcel(id, uuid, pickupAddress, destinationAddress, status, owner, recipientName, driver);
        }
    }
}
