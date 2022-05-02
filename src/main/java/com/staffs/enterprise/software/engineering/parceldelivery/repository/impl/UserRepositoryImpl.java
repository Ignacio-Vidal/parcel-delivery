package com.staffs.enterprise.software.engineering.parceldelivery.repository.impl;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.repository.UserRepository;
import com.staffs.enterprise.software.engineering.parceldelivery.repository.mappers.AppUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.query(sql, new AppUserMapper(), email).stream().findFirst();
    }

    @Override
    public int save(AppUser user) {
        String sql = "INSERT INTO users (uuid, name, email, password, role) VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(sql, user.getUuid(), user.getName(), user.getEmail(), user.getPassword(), user.getRole().toString());
    }
}
