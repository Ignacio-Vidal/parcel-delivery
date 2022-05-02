package com.staffs.enterprise.software.engineering.parceldelivery.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST, reason = "User already registered")
public class UserAlreadyRegistered extends UsernameNotFoundException {
    public UserAlreadyRegistered(String msg) {
        super(msg);
    }

    public UserAlreadyRegistered(String msg, Throwable cause) {
        super(msg, cause);
    }
}
