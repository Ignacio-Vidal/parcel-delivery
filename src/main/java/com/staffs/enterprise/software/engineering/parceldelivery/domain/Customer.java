package com.staffs.enterprise.software.engineering.parceldelivery.domain;

import java.util.UUID;

public class Customer extends User {

    public Customer(String uuid, String name, String lastName, String email, String address) {
        super(uuid, name, lastName, email, address, Roles.CUSTOMER);
    }

    public Customer(String name, String lastName, String email, String address) {
        this(UUID.randomUUID().toString(), name, lastName, email, address);
    }


}
