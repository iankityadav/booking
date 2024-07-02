package com.bistro.booking;

import com.bistro.booking.model.Restaurant;
import com.bistro.booking.model.Role;
import com.bistro.booking.model.Users;
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
public class RestaurantControllerIntegrationTest {

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
        baseUrl = "http://localhost:" + port + "/api/restaurants";
        reservationRequestRepository.deleteAll();
        bookingRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testRegisterRestaurant() {
        Users manager = new Users();
        manager.setFullName("John Doe");
        manager.setEmail("manager@example.com");
        manager.setPassword(passwordEncoder.encode("password"));
        manager.setPhone("1234567890");
        manager.setRole(Role.MANAGER);
        manager.setCreatedAt(LocalDateTime.now());
        userRepository.save(manager);
        System.out.println("manager = " + manager);

        Restaurant restaurant = new Restaurant();
        restaurant.setManager(manager);
        restaurant.setName("Test Restaurant");
        restaurant.setCuisines("Italian");
        restaurant.setLocation("Downtown");
        restaurant.setWorkingDays("Mon-Sun");
        restaurant.setWorkingHours("10:00-22:00");
        restaurant.setTimeSlotInterval("30");
        restaurant.setCreatedAt(LocalDateTime.now());
        System.out.println("restaurant = " + restaurant);

        HttpHeaders headers = new HttpHeaders();
        String token = jwtService.generateToken(User.withUsername("manager@example.com").password("").roles(Role.MANAGER.name()).build());
        headers.setBearerAuth(token);
        HttpEntity<Restaurant> request = new HttpEntity<>(restaurant, headers);
        System.out.println("request = " + request);
        ResponseEntity<Restaurant> response = restTemplate.postForEntity(baseUrl, request, Restaurant.class);
        System.out.println("response = " + response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Restaurant");
    }

    @Test
    void testGetAllRestaurants() {
        Users manager = new Users();
        manager.setFullName("John Doe");
        manager.setEmail("manager@example.com");
        manager.setPassword(passwordEncoder.encode("password"));
        manager.setPhone("1234567890");
        manager.setRole(Role.MANAGER);
        manager.setCreatedAt(LocalDateTime.now());
        userRepository.save(manager);

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setManager(manager);
        restaurant1.setName("Test Restaurant 1");
        restaurant1.setCuisines("Italian");
        restaurant1.setLocation("Downtown");
        restaurant1.setWorkingDays("Mon-Sun");
        restaurant1.setWorkingHours("10:00-22:00");
        restaurant1.setTimeSlotInterval("30");
        restaurant1.setCreatedAt(LocalDateTime.now());
        restaurantRepository.save(restaurant1);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setManager(manager);
        restaurant2.setName("Test Restaurant 2");
        restaurant2.setCuisines("Mexican");
        restaurant2.setLocation("Uptown");
        restaurant2.setWorkingDays("Mon-Sun");
        restaurant2.setWorkingHours("10:00-22:00");
        restaurant2.setTimeSlotInterval("30");
        restaurant2.setCreatedAt(LocalDateTime.now());
        restaurantRepository.save(restaurant2);

        HttpHeaders headers = new HttpHeaders();
        String token = jwtService.generateToken(User.withUsername("manager@example.com").password("").roles(Role.MANAGER.name()).build());
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Restaurant[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Restaurant[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(2);
    }
}
