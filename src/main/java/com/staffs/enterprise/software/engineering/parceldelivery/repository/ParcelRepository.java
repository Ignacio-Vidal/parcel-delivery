package com.staffs.enterprise.software.engineering.parceldelivery.repository;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;

import java.util.List;
import java.util.Optional;

public interface ParcelRepository {
    List<Parcel> findAllByStatus(String status);

    Optional<Parcel> findByUuid(String id);

    int save(Parcel parcel);

    void updateParcel(Parcel parcel);

    void deleteParcel(int id);
}
