package com.bistro.booking;

import com.bistro.booking.model.*;
import com.bistro.booking.repository.BookingRepository;
import com.bistro.booking.repository.UserRepository;
import com.bistro.booking.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/bookings";
//        bookingRepository.deleteAll();
//        userRepository.deleteAll();
    }


    @Test
    void testCancelBooking() {
        Users customer = new Users();
        customer.setFullName("John Doe");
        customer.setEmail("customer@example.com");
        customer.setPassword("password");
        customer.setPhone("1234567890");
        customer.setRole(Role.CUSTOMER);
        customer.setCreatedAt(LocalDateTime.now());
        userRepository.save(customer);

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setDate(LocalDateTime.now().plusDays(1));
        booking.setNumberOfGuests(2);
        booking.setTimeSlot("18:00");
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        bookingRepository.save(booking);

        restTemplate.delete(baseUrl + "/" + booking.getId());

        Booking cancelledBooking = bookingRepository.findById(booking.getId()).orElseThrow();
        assertThat(cancelledBooking.getStatus()).isEqualTo(BookingStatus.PENDING);
    }
}
