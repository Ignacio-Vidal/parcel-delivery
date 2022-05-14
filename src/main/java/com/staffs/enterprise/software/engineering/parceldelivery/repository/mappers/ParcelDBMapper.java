package com.staffs.enterprise.software.engineering.parceldelivery.repository.mappers;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.Parcel;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.ParcelStatus;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParcelDBMapper implements RowMapper<Parcel> {

    private final Logger log = LoggerFactory.getLogger(ParcelDBMapper.class);

    @Override
    public Parcel mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Parcel.Builder()
                .withPickupAddress(rs.getString("pick"))
                .withDestinationAddress(rs.getString("dest"))
                .withStatus(ParcelStatus.valueOf(rs.getString("status")))
                .withUuid(rs.getString("uuid"))
                .withOwner(rs.getString("owner"))
                .withRecipientName(rs.getString("recipient"))
                .withDriver(rs.getString("driver"))
                .build();
    }
}
