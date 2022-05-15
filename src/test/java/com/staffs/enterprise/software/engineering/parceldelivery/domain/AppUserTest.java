package com.staffs.enterprise.software.engineering.parceldelivery.domain;

import org.junit.jupiter.api.Test;

public class AppUserTest {
    @Test
    void assignUUID(){
        AppUser user = AppUser.create("ignacio", "ignacio@gmail.com", "my_password", Roles.CUSTOMER);
        assert user.getUuid() != null;
    }

    @Test
    void useExistingUUID(){
        AppUser user = AppUser.create(1001L,"1001-00001-0001","ignacio", "ignacio@gmail.com", "my_password", Roles.CUSTOMER);
        assert user.getUuid().equals("1001-00001-0001");
    }
}
