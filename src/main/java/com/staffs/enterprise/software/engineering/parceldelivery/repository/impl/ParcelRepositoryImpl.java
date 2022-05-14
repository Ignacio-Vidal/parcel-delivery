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
        String query = "SELECT p.id as pid, p.uuid as puuid,  p.pickup_address as ppick, p.destination_address as pdest, p.status as pstatus, p.recipient_name as precipient, u.id as uid, u.uuid as uuuid, u.name as uname, u.email as uemail, u.password as upassword, u.created as ucreated, u.role as urole " +
                "FROM parcels p " +
                "LEFT JOIN parcel_owner pu ON p.uuid = pu.parcel_uuid " +
                "LEFT JOIN  users u on u.uuid = pu.user_uuid " +
                "WHERE p.status=? ";
        List<Parcel> parcels = jdbcTemplate.query(query, new ParcelDBMapper(), status);
        return parcels;
    }

    @Override
    public Optional<Parcel> findByUuid(String uuid) {
        String query = "SELECT p.id as pid, p.uuid as puuid,  p.pickup_address as ppick, p.destination_address as pdest, p.status as pstatus, p.recipient_name as precipient, u.id as uid, u.uuid as uuuid, u.name as uname, u.email as uemail, u.password as upassword, u.created as ucreated, u.role as urole " +
                "FROM parcels p " +
                "LEFT JOIN parcel_owner pu ON p.uuid = pu.parcel_uuid " +
                "LEFT JOIN  users u on u.uuid = pu.user_uuid " +
                "WHERE p.uuid=? ";
        Optional<Parcel> parcel = jdbcTemplate.query(query, new ParcelDBMapper(), uuid).stream().findFirst();
        return parcel;
    }

    @Override
    public int save(Parcel parcel) {
        String parcelQuery = "INSERT INTO parcels(uuid, pickup_address, destination_address, status, recipient_name) VALUES (?, ?, ?, ?, ?)";
        int parcelId = jdbcTemplate.update(parcelQuery, parcel.getUuid(), parcel.getPickupAddress(), parcel.getDestinationAddress(), parcel.getStatus().toString(), parcel.getRecipientName());

        String parcelOwnerQuery = "INSERT INTO parcel_owner(parcel_uuid, user_uuid) VALUES (?, ?)";
        int joinId = jdbcTemplate.update(parcelOwnerQuery, parcel.getUuid(), parcel.getOwner().getUuid());
        return parcelId;
    }

    @Override
    public void updateParcel(Parcel parcel) {
        String parcelQuery = "UPDATE parcels SET status=? WHERE uuid=?";
        jdbcTemplate.update(parcelQuery, parcel.getStatus().toString(), parcel.getUuid());

        String parcelOwnerQuery = "UPDATE parcel_owner SET user_uuid=? WHERE parcel_uuid=?";
        jdbcTemplate.update(parcelOwnerQuery, parcel.getOwner().getUuid(), parcel.getUuid());

        if (parcel.getDriver() != null) {
            String parcelDriverQuery = "UPDATE parcel_driver SET user_uuid=? WHERE parcel_uuid=?";
            jdbcTemplate.update(parcelDriverQuery, parcel.getDriver().getUuid(), parcel.getUuid());
        }
    }

    @Override
    public void deleteParcel(int id) {

    }
}
