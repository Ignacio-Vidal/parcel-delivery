package com.staffs.enterprise.software.engineering.parceldelivery.domain;

import java.util.Objects;
import java.util.UUID;

public abstract class User {
    String uuid;
    String name;
    String lastName;
    String email;
    String address;
    Roles role;

    public User(String uuid, String name,String lastName, String email, String address, Roles role) {
        this.uuid = uuid;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    public User(String name,String lastName, String email, String address, Roles role) {
        this(UUID.randomUUID().toString(),name,lastName, email, address, role);
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uuid.equals(user.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

