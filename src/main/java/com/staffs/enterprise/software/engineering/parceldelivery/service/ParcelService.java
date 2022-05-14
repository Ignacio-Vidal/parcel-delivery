package com.staffs.enterprise.software.engineering.parceldelivery.service;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.updateParcelAction.BaseUpdateParcelAction;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.updateParcelAction.ParcelUpdateActionDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ParcelService {

    public List<Parcel> getParcelsByStatus(String status);

    public String registerParcel(Parcel parcel);

    public Optional<Parcel> getParcelById(String uuid);

    public Parcel updateParcel(ParcelUpdateActionDTO parcel);

    public Parcel deleteParcel(String id);
}
