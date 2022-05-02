package com.staffs.enterprise.software.engineering.parceldelivery.repository;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;

import java.util.Optional;

public interface UserRepository {
    Optional<AppUser> findByEmail(String email);
    int save(AppUser user);
}
