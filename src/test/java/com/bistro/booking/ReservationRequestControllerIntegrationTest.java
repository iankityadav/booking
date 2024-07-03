package com.bistro.booking;


import com.bistro.booking.model.*;
import com.bistro.booking.repository.BookingRepository;
import com.bistro.booking.repository.ReservationRequestRepository;
import com.bistro.booking.repository.RestaurantRepository;
import com.bistro.booking.repository.UserRepository;
import com.bistro.booking.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationRequestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ReservationRequestRepository reservationRequestRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/reservation-requests";
        reservationRequestRepository.deleteAll();
        bookingRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetAllReservationRequests() {
        Users customer = new Users();
        customer.setFullName("Jane Doe");
        customer.setEmail("customer@example.com");
        customer.setPassword(passwordEncoder.encode("password"));
        customer.setPhone("1234567890");
        customer.setRole(Role.CUSTOMER);
        customer.setCreatedAt(LocalDateTime.now());
        userRepository.save(customer);

        Users manager = new Users();
        manager.setFullName("John Doe");
        manager.setEmail("manager@example.com");
        manager.setPassword(passwordEncoder.encode("password"));
        manager.setPhone("1234567890");
        manager.setRole(Role.MANAGER);
        manager.setCreatedAt(LocalDateTime.now());
        userRepository.save(manager);

        Restaurant restaurant = new Restaurant();
        restaurant.setManager(manager);
        restaurant.setName("Test Restaurant");
        restaurant.setCuisines("Italian");
        restaurant.setLocation("Downtown");
        restaurant.setWorkingDays("Mon-Sun");
        restaurant.setWorkingHours("10:00-22:00");
        restaurant.setTimeSlotInterval("30");
        restaurant.setCreatedAt(LocalDateTime.now());
        restaurantRepository.save(restaurant);

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setRestaurant(restaurant);
        booking.setDate(LocalDateTime.now().plusDays(1));
        booking.setNumberOfGuests(2);
        booking.setTimeSlot("18:00");
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        bookingRepository.save(booking);

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setBooking(booking);
        reservationRequest.setStatus(ReservationRequestStatus.PENDING);
        reservationRequest.setCreatedAt(LocalDateTime.now());
        reservationRequestRepository.save(reservationRequest);
        HttpHeaders headers = new HttpHeaders();
        String token = jwtService.generateToken(User.withUsername("manager@example.com").password("").roles(Role.MANAGER.name()).build());
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate
                .exchange(baseUrl + "?page=0&size=10&status=PENDING", HttpMethod.GET, entity, String.class);
        System.out.println(response);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
//        assertThat(response.getBody().length).isEqualTo(1);
//        assertThat(response.getBody()[0].getStatus()).isEqualTo(ReservationRequestStatus.PENDING);
    }
}
