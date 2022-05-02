package com.staffs.enterprise.software.engineering.parceldelivery.domain;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Parcel {
    @Id
    private Long id;
    private String uuid;
    private String pickupAddress;
    private String destinationAddress;
    private ParcelStatus status;
    private AppUser user;

    private Parcel(Long id, String uuid, String pickupAddress, String destinationAddress, ParcelStatus status, AppUser user) {
        this.id = id;
        this.uuid = uuid;
        this.pickupAddress = pickupAddress;
        this.destinationAddress = destinationAddress;
        this.status = status;
        this.user = user;
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

    public AppUser getUser() {
        return user;
    }


    public static class Builder {
        private Long id;
        private String uuid;
        private String pickupAddress;
        private String destinationAddress;
        private ParcelStatus status;
        private AppUser user;

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

        public Builder withUser(AppUser user) {
            this.user = user;
            return this;
        }

        public Parcel build() {
            if (uuid == null) {
                this.uuid = UUID.randomUUID().toString();
            }
            return new Parcel(id, uuid, pickupAddress, destinationAddress, status, user);
        }
    }
}
