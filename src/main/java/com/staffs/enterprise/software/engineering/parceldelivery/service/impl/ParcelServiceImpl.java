package com.staffs.enterprise.software.engineering.parceldelivery.service.impl;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;
import com.staffs.enterprise.software.engineering.parceldelivery.repository.ParcelRepository;
import com.staffs.enterprise.software.engineering.parceldelivery.service.ParcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelServiceImpl implements ParcelService {
    private final ParcelRepository parcelRepository;
    private final Logger log = LoggerFactory.getLogger(ParcelServiceImpl.class);

    public ParcelServiceImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public List<Parcel> getParcelsByStatus(String status) {
        List<Parcel> parcels = parcelRepository.findAllByStatus(status);
        log.info("Parcels with status={} identified", status);
        return parcels;
    }

    @Override
    public Optional<Parcel> getParcelById(String uuid) {
        Optional<Parcel> parcel =  parcelRepository.findById(uuid);
        log.info("Parcel with uuid={} identified", uuid);
        return parcel;
    }

    @Override
    public String registerParcel(Parcel parcel) {
        parcelRepository.save(parcel);
        return parcel.getUuid();
    }

    @Override
    public Parcel updateParcel(Parcel parcel) {
        return null;
    }

    @Override
    public Parcel deleteParcel(String id) {
        return null;
    }
}
