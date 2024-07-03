package com.bistro.booking.repository;

import com.bistro.booking.model.Booking;
import com.bistro.booking.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAllByCustomerId(Long customerId, Pageable pageable);
}
