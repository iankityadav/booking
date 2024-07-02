package com.bistro.booking.repository;

import com.bistro.booking.model.ReservationRequest;
import com.bistro.booking.model.ReservationRequestStatus;
import com.bistro.booking.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {
    List<ReservationRequest> findAllByStatus(ReservationRequestStatus status, PageRequest of);

    Page<ReservationRequest> findAllByBookingRestaurantManagerId(Long managerId, Pageable pageable);

    Page<ReservationRequest> findAllByBookingRestaurantManagerIdAndStatus(Long managerId, ReservationRequestStatus status, Pageable pageable);
}
