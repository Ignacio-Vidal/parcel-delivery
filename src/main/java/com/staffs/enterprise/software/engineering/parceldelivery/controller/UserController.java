package com.staffs.enterprise.software.engineering.parceldelivery.controller;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.domain.Roles;
import com.staffs.enterprise.software.engineering.parceldelivery.dto.user.RegisterUserDTO;
import com.staffs.enterprise.software.engineering.parceldelivery.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public int register(@Valid @RequestBody RegisterUserDTO dto) {
        log.info("Creating user with email={}", dto.getEmail());
        String hashed_password = passwordEncoder.encode(dto.getPassword());
        AppUser user = AppUser.create(dto.getName(), dto.getEmail(), hashed_password, Roles.valueOf(dto.getRole().toString()));
        return userService.saveUser(user);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
