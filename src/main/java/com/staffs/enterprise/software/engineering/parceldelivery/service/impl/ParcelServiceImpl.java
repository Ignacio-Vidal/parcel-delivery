package com.staffs.enterprise.software.engineering.parceldelivery.service.impl;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.ParcelUpdateAction;
import com.staffs.enterprise.software.engineering.parceldelivery.exceptions.NotFoundException;
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
        Optional<Parcel> parcel = parcelRepository.findByUuid(uuid);
        log.info("Parcel with uuid={} identified", uuid);
        return parcel;
    }

    @Override
    public String registerParcel(Parcel parcel) {
        log.info(parcel.toString());
        parcelRepository.save(parcel);
        log.info("Parcel with uuid={} registered", parcel.getUuid());
        return parcel.getUuid();
    }

    @Override
    public Parcel updateParcel(ParcelUpdateAction action) {
        Optional<Parcel> maybeCurrentParcel = parcelRepository.findByUuid(action.getParcelUuid());
        if (maybeCurrentParcel.isEmpty()) {
            log.error("Parcel with uuid={} not found", action.getParcelUuid());
            throw new NotFoundException("Parcel with uuid=" + action.getParcelUuid() + " not found");
        }
        Parcel currentParcel = maybeCurrentParcel.get();
        switch (action.getAction()) {
            case DROP_PARCEL:
                currentParcel.readyForAllocation();
                break;
            case BOOKED_FOR_LOCAL_COLLECTION_BY_CUSTOMER:
                currentParcel.bookedForCustomerCollection();
                break;
            case SELECTED_BY_DRIVER:
                currentParcel.assignDelivery(action.getDriver());
                break;
            case COLLECTED_BY_DRIVER:
                currentParcel.outForDelivery();
                break;
            case DELIVERED_BY_DRIVER:
            case COLLECTED_LOCALLY_BY_CUSTOMER:
                currentParcel.delivered();
                break;
            case RETURNED_BY_DRIVER:
                currentParcel.returned();
                break;
            case REJECTED_BY_CUSTOMER:
                currentParcel.rejected();
                break;
            default:
                throw new IllegalStateException("unknown status for action=" + action.getAction());
        }
        parcelRepository.updateParcel(currentParcel);
        return currentParcel;
    }

    @Override
    public Parcel deleteParcel(String id) {
        return null;
    }
}
