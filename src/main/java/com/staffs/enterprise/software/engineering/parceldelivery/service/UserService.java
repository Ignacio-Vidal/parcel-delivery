package com.staffs.enterprise.software.engineering.parceldelivery.service;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

public interface UserService {
    int saveUser(AppUser user);
    AppUser findUserByEmail(String email);
}
