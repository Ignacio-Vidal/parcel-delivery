package com.staffs.enterprise.software.engineering.parceldelivery.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParcelTest {
    private AppUser user;

    @BeforeEach
    public void setUp() {
        user = AppUser.create("Ignacio", "test@gmail.com", "12345678", Roles.CUSTOMER);
    }

    @Test
    public void readyForAllocationFromRegistered() {
        Parcel parcel = createParcel(user, ParcelStatus.REGISTERED);
        parcel.readyForAllocation();
        assert parcel.getStatus() == ParcelStatus.READY_FOR_ALLOCATION;
    }

    @Test
    public void readyForAllocationFromRejected() {
        Parcel parcel = createParcel(user, ParcelStatus.REJECTED_BY_CUSTOMER);
        parcel.readyForAllocation();
        assert parcel.getStatus() == ParcelStatus.READY_FOR_ALLOCATION;
    }

    @Test
    void readyForAllocationThrowsException() {
        Parcel parcel = createParcel(user, ParcelStatus.OUT_FOR_DELIVERY);
        assertThrows(IllegalStateException.class, parcel::readyForAllocation);

    }

    @Test
    void assignForDeliveryOk() {
        Parcel parcel = createParcel(null, ParcelStatus.READY_FOR_ALLOCATION);
        parcel.assignDelivery(user);
        assert parcel.getStatus() == ParcelStatus.DELIVERY_ASSIGNED;
        assert parcel.getUser().equals(user);
    }

    @Test
    void assignForDeliveryThrowsUserAlreadyAssigned() {
        Parcel parcel = createParcel(user, ParcelStatus.READY_FOR_ALLOCATION);
        assertThrows(IllegalStateException.class, () -> parcel.assignDelivery(user));
    }

    @Test
    void assignForDeliveryThrowsWrongStatus() {
        Parcel parcel = createParcel(null, ParcelStatus.OUT_FOR_DELIVERY);
        assertThrows(IllegalStateException.class, () -> parcel.assignDelivery(user));
    }

    @Test
    void outForDeliveryOk() {
        Parcel parcel = createParcel(user, ParcelStatus.DELIVERY_ASSIGNED);
        parcel.outForDelivery();
    }

    @Test
    void outForDeliveryThrowsWrongStatus() {
        Parcel parcel = createParcel(user, ParcelStatus.OUT_FOR_DELIVERY);
        assertThrows(IllegalStateException.class, parcel::outForDelivery);
    }

    @Test
    void deliveredOk() {
        Parcel parcel = createParcel(user, ParcelStatus.OUT_FOR_DELIVERY);
        parcel.delivered();
        assert parcel.getStatus() == ParcelStatus.DELIVERED;
    }

    @Test
    void deliveredThrowsWrongStatus() {
        Parcel parcel = createParcel(user, ParcelStatus.DELIVERED);
        assertThrows(IllegalStateException.class, parcel::delivered);
    }

    @Test
    void returnedOk() {
        Parcel parcel = createParcel(user, ParcelStatus.OUT_FOR_DELIVERY);
        parcel.returned();
        assert parcel.getStatus() == ParcelStatus.READY_FOR_ALLOCATION;
        assert parcel.getUser() == null;
    }

    @Test
    void returnedThrowsWrongStatus() {
        Parcel parcel = createParcel(user, ParcelStatus.READY_FOR_ALLOCATION);
        assertThrows(IllegalStateException.class, parcel::returned);
    }

    private Parcel createParcel(AppUser user, ParcelStatus status) {
        Parcel.Builder builder = new Parcel.Builder();
        return builder.withUuid(UUID.randomUUID().toString())
                .withDestinationAddress("destinationAddress")
                .withPickupAddress("pickupAddress")
                .withStatus(status)
                .withUser(user)
                .build();
    }
}
