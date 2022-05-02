package com.staffs.enterprise.software.engineering.parceldelivery.repository.mappers;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.Roles;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserMapper implements RowMapper<AppUser> {
    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AppUser.create(
                rs.getLong("id"),
                rs.getString("uuid"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                Roles.valueOf(rs.getString("role"))
                );
    }
}
