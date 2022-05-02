package com.staffs.enterprise.software.engineering.parceldelivery.config;

import com.staffs.enterprise.software.engineering.parceldelivery.filter.AuthZFilter;
import com.staffs.enterprise.software.engineering.parceldelivery.filter.JWTFilter;
import com.staffs.enterprise.software.engineering.parceldelivery.filter.UserFilter;
import com.staffs.enterprise.software.engineering.parceldelivery.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/actuator/*").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/users/register").permitAll();
        http.authorizeRequests().antMatchers("/parcels").hasAnyAuthority("DRIVER", "CUSTOMER");
        http.addFilter(new JWTFilter(authenticationManagerBean()));
        http.addFilterBefore(new AuthZFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new UserFilter(userService), AuthZFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
