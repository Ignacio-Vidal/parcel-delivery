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
    public void readyForAllocationFromRegisteredOk() {
        Parcel parcel = createParcel(user, ParcelStatus.REGISTERED);
        parcel.readyForAllocation();
        assert parcel.getStatus() == ParcelStatus.READY_FOR_ALLOCATION;
    }

    @Test
    public void readyForAllocationFromOutForDeliveryOk() {
        Parcel parcel = createParcel(user, ParcelStatus.OUT_FOR_DELIVERY);
        parcel.readyForAllocation();
        assert parcel.getStatus() == ParcelStatus.READY_FOR_ALLOCATION;
    }

    @Test
    void readyForAllocationThrowsException() {
        Parcel parcel = createParcel(user, ParcelStatus.DELIVERED);
        assertThrows(IllegalStateException.class, parcel::readyForAllocation);
    }

    @Test
    void assignForDeliveryOk() {
        Parcel parcel = createParcel(user, ParcelStatus.READY_FOR_ALLOCATION);
        parcel.assignDelivery(user);
        assert parcel.getStatus() == ParcelStatus.DELIVERY_ASSIGNED;
        assert parcel.getDriver().equals(user.getUuid());
    }

    @Test
    void assignForDeliveryThrowsWrongStatus() {
        Parcel parcel = createParcel(user, ParcelStatus.OUT_FOR_DELIVERY);
        assertThrows(IllegalStateException.class, () -> parcel.assignDelivery(user));
    }

    @Test
    void outForDeliveryOk() {
        Parcel parcel = createParcel(user, ParcelStatus.DELIVERY_ASSIGNED);
        parcel.outForDelivery();
    }

    @Test
    void outForDeliveryFails() {
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
    void deliveredThrows() {
        Parcel parcel = createParcel(user, ParcelStatus.DELIVERED);
        assertThrows(IllegalStateException.class, parcel::delivered);
    }

    @Test
    void returnedOk() {
        Parcel parcel = createParcel(user, ParcelStatus.OUT_FOR_DELIVERY);
        parcel.returned();
        assert parcel.getStatus() == ParcelStatus.READY_FOR_ALLOCATION;
        assert parcel.getDriver() == null;
    }

    @Test
    void returnedThrows() {
        Parcel parcel = createParcel(user, ParcelStatus.READY_FOR_ALLOCATION);
        assertThrows(IllegalStateException.class, parcel::returned);
    }

    @Test
    void rejectedOk(){
        Parcel parcel = createParcel(user, ParcelStatus.OUT_FOR_DELIVERY);
        parcel.rejected();
        assert parcel.getStatus() == ParcelStatus.REJECTED_BY_CUSTOMER;
    }

    @Test
    void rejectedThrows(){
        Parcel parcel = createParcel(user, ParcelStatus.READY_FOR_ALLOCATION);
        assertThrows(IllegalStateException.class, parcel::rejected);
    }

    @Test
    void bookedForCollectionOk(){
        Parcel parcel = createParcel(user, ParcelStatus.READY_FOR_ALLOCATION);
        parcel.bookedForCustomerCollection();
        assert parcel.getStatus() == ParcelStatus.BOOKED_FOR_COLLECTION;
    }

    @Test
    void bookedForCollectionThrows(){
        Parcel parcel = createParcel(user, ParcelStatus.OUT_FOR_DELIVERY);
        assertThrows(IllegalStateException.class, parcel::bookedForCustomerCollection);
    }

    private Parcel createParcel(AppUser user, ParcelStatus status) {
        Parcel.Builder builder = new Parcel.Builder();
        return builder.withUuid(UUID.randomUUID().toString())
                .withDestinationAddress("destinationAddress")
                .withPickupAddress("pickupAddress")
                .withStatus(status)
                .withOwner(user.getUuid())
                .withDriver(null)
                .build();
    }
}
