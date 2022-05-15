package com.staffs.enterprise.software.engineering.parceldelivery.repository.impl;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;
import com.staffs.enterprise.software.engineering.parceldelivery.repository.ParcelRepository;
import com.staffs.enterprise.software.engineering.parceldelivery.repository.mappers.ParcelDBMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ParcelRepositoryImpl implements ParcelRepository {
    private final JdbcTemplate jdbcTemplate;

    public ParcelRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Parcel> findAllByStatus(String status) {
        String query = "SELECT p.id                  as id,\n" +
                "       p.uuid                as uuid,\n" +
                "       p.pickup_address      as pick,\n" +
                "       p.destination_address as dest,\n" +
                "       p.status              as status,\n" +
                "       p.recipient_name      as recipient,\n" +
                "       pd.driver             as driver,\n" +
                "       po.owner              as owner\n" +
                "FROM parcels p\n" +
                "         LEFT JOIN parcel_owner po ON p.uuid = po.parcel_uuid\n" +
                "         LEFT JOIN parcel_driver pd on p.uuid = pd.parcel_uuid\n" +
                "WHERE p.status = ?;";
        List<Parcel> parcels = jdbcTemplate.query(query, new ParcelDBMapper(), status);
        return parcels;
    }

    @Override
    public Optional<Parcel> findByUuid(String uuid) {
        String query = "SELECT p.id                  as id,\n" +
                "       p.uuid                as uuid,\n" +
                "       p.pickup_address      as pick,\n" +
                "       p.destination_address as dest,\n" +
                "       p.status              as status,\n" +
                "       p.recipient_name      as recipient,\n" +
                "       pd.driver             as driver,\n" +
                "       po.owner              as owner\n" +
                "FROM parcels p\n" +
                "         LEFT JOIN parcel_owner po ON p.uuid = po.parcel_uuid\n" +
                "         LEFT JOIN parcel_driver pd on p.uuid = pd.parcel_uuid\n" +
                "WHERE p.uuid = ?;";
        Optional<Parcel> parcel = jdbcTemplate.query(query, new ParcelDBMapper(), uuid).stream().findFirst();
        return parcel;
    }

    @Override
    public int save(Parcel parcel) {
        String parcelQuery = "INSERT INTO parcels(uuid, pickup_address, destination_address, status, recipient_name) VALUES (?, ?, ?, ?, ?)";
        int parcelId = jdbcTemplate.update(parcelQuery, parcel.getUuid(), parcel.getPickupAddress(), parcel.getDestinationAddress(), parcel.getStatus().toString(), parcel.getRecipientName());

        String parcelOwnerQuery = "INSERT INTO parcel_owner(parcel_uuid, owner) VALUES (?, ?)";
        int joinId = jdbcTemplate.update(parcelOwnerQuery, parcel.getUuid(), parcel.getOwner());
        return parcelId;
    }

    @Override
    public void updateParcel(Parcel parcel) {
        String parcelQuery = "UPDATE parcels SET status=? WHERE uuid=?";
        jdbcTemplate.update(parcelQuery, parcel.getStatus().toString(), parcel.getUuid());

        String parcelOwnerQuery = "UPDATE parcel_owner SET owner=? WHERE parcel_uuid=?";
        jdbcTemplate.update(parcelOwnerQuery, parcel.getOwner(), parcel.getUuid());

        if (parcel.getDriver() != null) {
            String parcelDriverQuery = "UPDATE parcel_driver SET driver=? WHERE parcel_uuid=?";
            jdbcTemplate.update(parcelDriverQuery, parcel.getDriver(), parcel.getUuid());
        }
    }

    @Override
    public void deleteParcel(String uuid) {
        String deleteOwner = "DELETE FROM parcel_owner WHERE parcel_uuid=?";
        String deleteDriver = "DELETE FROM parcel_driver WHERE parcel_uuid=?";
        String deleteParcel = "DELETE FROM parcels WHERE uuid=?";
        jdbcTemplate.update(deleteOwner, uuid);
        jdbcTemplate.update(deleteDriver, uuid);
        jdbcTemplate.update(deleteParcel, uuid);
    }
}
