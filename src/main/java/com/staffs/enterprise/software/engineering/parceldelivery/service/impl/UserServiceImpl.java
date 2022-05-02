package com.staffs.enterprise.software.engineering.parceldelivery.service.impl;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.exceptions.UserAlreadyRegistered;
import com.staffs.enterprise.software.engineering.parceldelivery.repository.UserRepository;
import com.staffs.enterprise.software.engineering.parceldelivery.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public int saveUser(AppUser user) {
        log.info("Saving user with email={}", user.getEmail());
        userRepository.findByEmail(user.getEmail()).ifPresent(
                u -> {
                    log.info("User with email {} already exists", user.getEmail());
                    throw new UserAlreadyRegistered("User with email " + user.getEmail() + " already exists");
                });
        int id = userRepository.save(user);
        log.info("User saved with id {}", id);
        return id;
    }

    @Override
    public AppUser findUserByEmail(String email) {
        Optional<AppUser> maybeUser = userRepository.findByEmail(email);
        if (maybeUser.isEmpty()) {
            log.info("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        AppUser appUser = maybeUser.get();
        log.info("User found with uuid={}", appUser.getUuid());
        return appUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserAlreadyRegistered {
        Optional<AppUser> maybeUser = userRepository.findByEmail(email);
        if (maybeUser.isEmpty()) {
            log.info("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            AppUser user = maybeUser.get();
            log.info("User found with uuid={}", user.getUuid());
            Collection<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().toString()));
            return new User(user.getEmail(), user.getPassword(), authorities);
        }

    }
}
