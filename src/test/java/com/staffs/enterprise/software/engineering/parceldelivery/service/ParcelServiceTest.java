package com.staffs.enterprise.software.engineering.parceldelivery.service;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.*;
import com.staffs.enterprise.software.engineering.parceldelivery.repository.ParcelRepository;
import com.staffs.enterprise.software.engineering.parceldelivery.service.impl.ParcelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParcelServiceTest {
    @Mock
    private ParcelRepository parcelRepository;
    static String uuid = "a7a638df-f747-444a-a103-ee7b9d7d1708";
    ParcelService parcelService;
    static Parcel parcel;

    @BeforeEach
    void initialiseService() {
        parcelService = new ParcelServiceImpl(parcelRepository);
        parcel = create();
    }

    @Test
    void getParcelsByStatus() {
        when(parcelRepository.findAllByStatus(ParcelStatus.REGISTERED.toString())).thenReturn(List.of(parcel));
        List<Parcel> parcels = parcelService.getParcelsByStatus(ParcelStatus.REGISTERED.toString());
        assert parcels.size() == 1;
    }

    @Test
    void getParcelById(){
        when(parcelRepository.findByUuid(uuid)).thenReturn(Optional.of(parcel));
        Optional<Parcel> parcel = parcelService.getParcelById(uuid);
        assertEquals(parcel.get().getUuid(), uuid);
    }

    @Test
    void registerParcel(){
        Parcel myParcel = create();
        when(parcelRepository.save(myParcel)).thenReturn(1);
        String uuid = parcelService.registerParcel(myParcel);
        assertEquals(uuid, myParcel.getUuid());
    }

    @Test
    void parcelDropped(){
        Parcel myParcel = create();
        doNothing().when(parcelRepository).updateParcel(myParcel);
        when(parcelRepository.findByUuid(myParcel.getUuid())).thenReturn(Optional.of(myParcel));
        AppUser user = createUser();
        ParcelUpdateAction action = ParcelUpdateAction.create(uuid, UpdateActions.DROP_PARCEL, user);
        Parcel updatedParcel = parcelService.updateParcel(action);
        assert updatedParcel.getStatus() == ParcelStatus.READY_FOR_ALLOCATION;
    }

    @Test
    void parcelAssignedToDriver(){
        Parcel myParcel = create();
        doNothing().when(parcelRepository).updateParcel(myParcel);
        when(parcelRepository.findByUuid(myParcel.getUuid())).thenReturn(Optional.of(myParcel));
        AppUser user = createUser();
        ParcelUpdateAction action = ParcelUpdateAction.create(uuid, UpdateActions.SELECTED_BY_DRIVER, user);
        Parcel updatedParcel = parcelService.updateParcel(action);
        assert updatedParcel.getStatus() == ParcelStatus.DELIVERY_ASSIGNED;
    }

    static Parcel create() {
        Parcel.Builder builder = new Parcel.Builder();
        return builder
                .withUuid(uuid)
                .withStatus(ParcelStatus.REGISTERED)
                .withDriver(null)
                .withPickupAddress("Staffs university")
                .withRecipientName("John Doe")
                .withOwner("Ignacio")
                .withDestinationAddress("London")
                .build();
    }

    static AppUser createUser(){
        return AppUser.create("John Doe", "johndoe@gmail.com", "secret_password", Roles.CUSTOMER);
    }
}


