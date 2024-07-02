package com.bistro.booking.repository;

import com.bistro.booking.model.Booking;
import com.bistro.booking.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
