package com.bistro.booking.repository;

import com.bistro.booking.model.Restaurant;
import com.bistro.booking.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
}
