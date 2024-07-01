package com.bistro.booking.service;

import com.bistro.booking.dto.AuthenticationRequest;
import com.bistro.booking.dto.AuthenticationResponse;
import com.bistro.booking.dto.RegisterRequest;
import com.bistro.booking.model.Role;
import com.bistro.booking.model.Users;
import com.bistro.booking.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

/**
 * Service to authenticate and register user into application
 */
@Service
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Save the user credentials with role
     * User with age above 18 are only allowed to register into application
     *
     * @param request
     * @return token to authenticate
     */
    public AuthenticationResponse register(RegisterRequest request) {
        Role role = request.getRole() == 1 ? Role.MANAGER : Role.CUSTOMER;
        Users user;
        ObjectMapper mapper = new ObjectMapper();
        try {
            user = mapper.readValue(mapper.writeValueAsString(request), Users.class);
            logger.info("user: {}", user);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        if (userRepository.findByEmail(request.getEmail()).isEmpty()) {
            userRepository.save(user);
        }
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role.name()));
        String jwtToken = jwtService.generateToken(new User(user.getUsername(), user.getPassword(), user.getAuthorities()));

        return new AuthenticationResponse(jwtToken);
    }

    /**
     * Method to authenticate user with credentials
     * @param request
     * @return token to authenticate
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        Users user = userRepository.findByEmail(request.getUsername()).orElseThrow();
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        var jwtToken = jwtService.generateToken(new User(user.getUsername(), user.getPassword(), user.getAuthorities()));
        return new AuthenticationResponse(jwtToken);
    }
}
