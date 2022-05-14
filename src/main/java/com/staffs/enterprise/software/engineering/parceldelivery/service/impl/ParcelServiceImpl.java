package com.staffs.enterprise.software.engineering.parceldelivery.service.impl;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.ParcelStatus;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.updateParcelAction.BaseUpdateParcelAction;
import com.staffs.enterprise.software.engineering.parceldelivery.exceptions.NotFoundException;
import com.staffs.enterprise.software.engineering.parceldelivery.repository.ParcelRepository;
import com.staffs.enterprise.software.engineering.parceldelivery.repository.UserRepository;
import com.staffs.enterprise.software.engineering.parceldelivery.service.ParcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelServiceImpl implements ParcelService {
    private final ParcelRepository parcelRepository;
    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(ParcelServiceImpl.class);

    public ParcelServiceImpl(ParcelRepository parcelRepository, UserRepository userRepository) {
        this.parcelRepository = parcelRepository;
        this.userRepository = userRepository;
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
    public Parcel updateParcel(BaseUpdateParcelAction action) {
        Optional<Parcel> maybeCurrentParcel = parcelRepository.findByUuid(action.getParcelUuid());
        if (maybeCurrentParcel.isEmpty()) {
            log.error("Parcel with uuid={} not found", action.getParcelUuid());
            throw new NotFoundException("Parcel with uuid=" + action.getParcelUuid() + " not found");
        }
        Parcel currentParcel = maybeCurrentParcel.get();
        switch (ParcelStatus.valueOf(action.getAction())) {
            case READY_FOR_ALLOCATION:
                currentParcel.readyForAllocation();
                break;
            case BOOKED_FOR_COLLECTION:
                break;
            case DELIVERY_ASSIGNED:

                break;
            case OUT_FOR_DELIVERY:
                currentParcel.outForDelivery();
                break;
            case DELIVERED:
                currentParcel.delivered();
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
