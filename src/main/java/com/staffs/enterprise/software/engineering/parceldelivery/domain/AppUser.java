package com.staffs.enterprise.software.engineering.parceldelivery.domain;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class AppUser {
    @Id
    private Long id;
    private String uuid;
    private String name;
    private String email;
    private String password;
    private Roles role;

    private AppUser(String uuid, String name, String email, String password, Roles role) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    private AppUser(Long id, String uuid, String name, String email, String password, Roles role) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static AppUser create(String name, String email, String password, Roles role) {
        return new AppUser(UUID.randomUUID().toString(), name, email, password, role);
    }

    public static AppUser create(Long id, String uuid, String name, String email, String password, Roles role) {
        return new AppUser(id, uuid, name, email, password, role);
    }

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Roles getRole() {
        return role;
    }
}
