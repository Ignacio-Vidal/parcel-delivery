package com.staffs.enterprise.software.engineering.parceldelivery.domain;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Parcel {
    @Id
    private Long id;
    private String uuid;
    private String pickupAddress;
    private String destinationAddress;
    private String recipientName;
    private ParcelStatus status;
    private String owner;
    private String driver;

    private Parcel(Long id, String uuid, String pickupAddress, String destinationAddress, ParcelStatus status, String owner, String recipientName, String driver) {
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

    public String getOwner() {
        return owner;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getDriver(){
        return driver;
    }

    public void readyForAllocation() {
        Set<ParcelStatus> statuses = Set.of(ParcelStatus.REGISTERED, ParcelStatus.OUT_FOR_DELIVERY);
        if (!statuses.contains(this.status)) {
            throw new IllegalStateException("Parcel is not REGISTERED or REJECTED_BY_CUSTOMER");
        }
        status = ParcelStatus.READY_FOR_ALLOCATION;
    }

    public void assignDelivery(AppUser user) {
        if (!(status == ParcelStatus.READY_FOR_ALLOCATION)) {
            throw new IllegalStateException("Parcel is not ready for allocation");
        }
        this.driver = user.getUuid();
        this.status = ParcelStatus.DELIVERY_ASSIGNED;
    }

    public void outForDelivery() {
        if (!(status == ParcelStatus.DELIVERY_ASSIGNED)) {
            throw new IllegalStateException("Parcel is not assigned for delivery");
        }
        status = ParcelStatus.OUT_FOR_DELIVERY;
    }

    public void delivered() {
        if (!List.of(ParcelStatus.OUT_FOR_DELIVERY, ParcelStatus.BOOKED_FOR_COLLECTION).contains(status)) {
            throw new IllegalStateException("Parcel is not out for delivery");
        }
        status = ParcelStatus.DELIVERED;
    }

    public void returned() {
        if (!(status == ParcelStatus.OUT_FOR_DELIVERY)) {
            throw new IllegalStateException("Parcel is not out for delivery");
        }
        status = ParcelStatus.READY_FOR_ALLOCATION;
        driver = null;
    }

    public void rejected() {
        if (!(status == ParcelStatus.OUT_FOR_DELIVERY)) {
            throw new IllegalStateException("Parcel is not out for delivery");
        }
        status = ParcelStatus.REJECTED_BY_CUSTOMER;
    }

    public void bookedForCustomerCollection(){
        if (!(status == ParcelStatus.READY_FOR_ALLOCATION)){
            throw new IllegalStateException("Parcel is not ready for allocation");
        }
        status = ParcelStatus.BOOKED_FOR_COLLECTION;
    }

    public static class Builder {
        private Long id;
        private String uuid;
        private String pickupAddress;
        private String destinationAddress;
        private ParcelStatus status;
        private String owner;
        private String driver;
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

        public Builder withOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder withRecipientName(String recipientName) {
            this.recipientName = recipientName;
            return this;
        }

        public Builder withDriver(String driver) {
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
