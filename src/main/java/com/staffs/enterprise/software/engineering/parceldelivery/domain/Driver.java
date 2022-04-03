package com.staffs.enterprise.software.engineering.parceldelivery.domain;

import java.util.UUID;

public class Driver extends User {

    public Driver(String uuid, String name, String lastName, String email, String address) {
        super(uuid, name, lastName, email, address, Roles.DRIVER);
    }

    public Driver(String name, String lastName, String email, String address) {
        this(UUID.randomUUID().toString(), name, lastName, email, address);
    }
}
