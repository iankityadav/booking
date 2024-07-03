package com.bistro.booking;

import com.bistro.booking.model.Booking;
import com.bistro.booking.model.BookingStatus;
import com.bistro.booking.model.Role;
import com.bistro.booking.model.Users;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        bookingRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testBookTable() {
        Users customer = new Users();
        customer.setFullName("John Doe");
        customer.setEmail("customer@example.com");
        customer.setPassword(passwordEncoder.encode("password"));
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

        ResponseEntity<Booking> response = restTemplate.postForEntity(baseUrl, booking, Booking.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(BookingStatus.PENDING);
    }

    @Test
    void testGetAllBookingsForCustomer() {
        Users customer = new Users();
        customer.setFullName("John Doe");
        customer.setEmail("customer@example.com");
        customer.setPassword("password");
        customer.setPhone("1234567890");
        customer.setRole(Role.CUSTOMER);
        customer.setCreatedAt(LocalDateTime.now());
        userRepository.save(customer);

        Booking booking1 = new Booking();
        booking1.setCustomer(customer);
        booking1.setDate(LocalDateTime.now().plusDays(1));
        booking1.setNumberOfGuests(2);
        booking1.setTimeSlot("18:00");
        booking1.setStatus(BookingStatus.PENDING);
        booking1.setCreatedAt(LocalDateTime.now());
        bookingRepository.save(booking1);

        Booking booking2 = new Booking();
        booking2.setCustomer(customer);
        booking2.setDate(LocalDateTime.now().plusDays(2));
        booking2.setNumberOfGuests(4);
        booking2.setTimeSlot("20:00");
        booking2.setStatus(BookingStatus.PENDING);
        booking2.setCreatedAt(LocalDateTime.now());
        bookingRepository.save(booking2);

        ResponseEntity<Booking[]> response = restTemplate.getForEntity(baseUrl + "/customer/" + customer.getId() + "?page=0&size=10", Booking[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(2);
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
        assertThat(cancelledBooking.getStatus()).isEqualTo(BookingStatus.CANCELLED);
    }
}
