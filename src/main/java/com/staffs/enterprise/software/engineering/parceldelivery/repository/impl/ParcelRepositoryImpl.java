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
//        String query = "SELECT * FROM parcels where status=?";
        String query = "SELECT p.id as pid, p.uuid as puuid,  p.pickup_address as ppick, p.destination_address as pdest, p.status as pstatus, u.id as uid, u.uuid as uuuid, u.name as uname, u.email as uemail, u.password as upassword, u.created as ucreated, u.role as urole " +
                "FROM parcels p " +
                "LEFT JOIN parcels_users pu ON p.uuid = pu.parcel_uuid " +
                "LEFT JOIN  users u on u.uuid = pu.user_uuid " +
                "WHERE p.status=? ";
        List<Parcel> parcels = jdbcTemplate.query(query, new ParcelDBMapper(), status);
        return parcels;
    }

    @Override
    public Optional<Parcel> findById(String uuid) {
        String query = "SELECT p.id as pid, p.uuid as puuid,  p.pickup_address as ppick, p.destination_address as pdest, p.status as pstatus, u.id as uid, u.uuid as uuuid, u.name as uname, u.email as uemail, u.password as upassword, u.created as ucreated, u.role as urole " +
                "FROM parcels p " +
                "LEFT JOIN parcels_users pu ON p.uuid = pu.parcel_uuid " +
                "LEFT JOIN  users u on u.uuid = pu.user_uuid " +
                "WHERE p.uuid=? ";
        Optional<Parcel> parcel = jdbcTemplate.query(query, new ParcelDBMapper(), uuid).stream().findFirst();
        return parcel;
    }

    @Override
    public int save(Parcel parcel) {
        String parcelQuery = "INSERT INTO parcels(uuid, pickup_address, destination_address, status) VALUES (?, ?, ?, ?)";
        int parcelId = jdbcTemplate.update(parcelQuery, parcel.getUuid(), parcel.getPickupAddress(), parcel.getDestinationAddress(), parcel.getStatus().toString());

        String joinQuery = "INSERT INTO parcels_users(parcel_uuid, user_uuid) VALUES (?, ?)";
        int joinId = jdbcTemplate.update(joinQuery, parcel.getUuid(), parcel.getUser().getUuid());
        return parcelId;
    }

    @Override
    public void updateParcel(Parcel parcel) {
    }

    @Override
    public void deleteParcel(int id) {

    }
}
