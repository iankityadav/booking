package com.bistro.booking.service;

import com.bistro.booking.model.Restaurant;
import com.bistro.booking.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Optional<Restaurant> findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }
}

