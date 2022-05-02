package com.staffs.enterprise.software.engineering.parceldelivery.filter;

import com.staffs.enterprise.software.engineering.parceldelivery.domain.AppUser;
import com.staffs.enterprise.software.engineering.parceldelivery.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UserFilter extends OncePerRequestFilter {
    private final UserService userService;

    public UserFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            AppUser user = userService.findUserByEmail(auth.getPrincipal().toString());
            request.setAttribute("user", user);
        }
        filterChain.doFilter(request, response);
    }
}
