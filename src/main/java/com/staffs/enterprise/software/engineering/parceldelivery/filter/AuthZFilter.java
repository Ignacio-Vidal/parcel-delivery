package com.staffs.enterprise.software.engineering.parceldelivery.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthZFilter extends OncePerRequestFilter {
    private Logger log = LoggerFactory.getLogger(AuthZFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Set<String> endpoint = Set.of("/login", "/users/register");
            if (endpoint.contains(request.getRequestURI())) {
                filterChain.doFilter(request, response);
            } else {
                String authToken = request.getHeader("Authorization");
                if (authToken != null || authToken.startsWith("Bearer ")) {
                    String token = authToken.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("super_secret_key".getBytes(StandardCharsets.UTF_8));
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String email = decodedJWT.getSubject();
                    List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                    List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    filterChain.doFilter(request, response);
                }
            }
        } catch (ServletException e) {
            log.error(e.getMessage());
            response.setStatus(400);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
