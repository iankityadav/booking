package com.bistro.booking;

import com.bistro.booking.dto.RestaurantInfo;
import com.bistro.booking.dto.RestaurantTableInfo;
import com.bistro.booking.model.Restaurant;
import com.bistro.booking.model.RestaurantTable;
import com.bistro.booking.repository.RestaurantRepository;
import com.bistro.booking.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindById() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        Optional<Restaurant> foundRestaurant = restaurantService.findById(1L);

        assertTrue(foundRestaurant.isPresent());
        assertEquals(1L, foundRestaurant.get().getRestaurantId());
    }

    @Test
    void testFindAll() {
        List<Restaurant> restaurants = Arrays.asList(new Restaurant(), new Restaurant());
        when(restaurantRepository.findAll()).thenReturn(restaurants);
        Page<Restaurant> foundRestaurants = restaurantService.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
        assertNull(foundRestaurants);
    }
}
