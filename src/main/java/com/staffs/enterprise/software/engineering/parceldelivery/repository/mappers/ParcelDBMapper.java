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
        AppUser user = AppUser.create(
                (long) rs.getInt("uid"),
                rs.getString("uuuid"),
                rs.getString("uname"),
                rs.getString("uemail"),
                rs.getString("upassword"),
                Roles.valueOf(rs.getString("urole"))
        );
        return new Parcel.Builder()
                .withPickupAddress(rs.getString("ppick"))
                .withDestinationAddress(rs.getString("pdest"))
                .withStatus(ParcelStatus.valueOf(rs.getString("pstatus")))
                .withUuid(rs.getString("puuid"))
                .withUser(user)
                .build();
    }
}
