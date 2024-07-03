package com.bistro.booking.service;

import com.bistro.booking.dto.RestaurantInfo;
import com.bistro.booking.model.Restaurant;
import com.bistro.booking.model.RestaurantTable;
import com.bistro.booking.repository.RestaurantRepository;
import com.bistro.booking.repository.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    public Restaurant saveRestaurant(RestaurantInfo restaurantInfo) {
        Restaurant restaurant = new Restaurant();
        RestaurantTable restaurantTable = new RestaurantTable();
        restaurant.setName(restaurantInfo.getName());
        restaurant.setLocation(restaurantInfo.getLocation());
        restaurant.setCuisines(restaurantInfo.getCuisines());
        restaurant.setTimeSlotInterval(restaurantInfo.getTimeSlotInterval());
        restaurant.setWorkingDays(restaurantInfo.getWorkingDays());
        restaurant.setWorkingHours(restaurantInfo.getWorkingHour());
        restaurant.setCreatedAt(restaurantInfo.getCreatedAt());
        restaurantTable.setRestaurant(restaurant);
        restaurantTable.setType(restaurantInfo.getRestaurantTableInfo().getTableType());
        restaurantTable.setNumber(restaurantInfo.getRestaurantTableInfo().getNumberOfTables());
        restaurantTable.setCreatedAt(restaurantInfo.getRestaurantTableInfo().getCreatedAt());
        restaurantRepository.save(restaurant);
        restaurantTableRepository.save(restaurantTable);
        return restaurant;
    }

    public Optional<Restaurant> findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    public Page<Restaurant> findAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }
}

