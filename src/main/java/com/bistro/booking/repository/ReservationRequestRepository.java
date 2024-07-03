package com.bistro.booking.repository;

import com.bistro.booking.model.ReservationRequest;
import com.bistro.booking.model.ReservationRequestStatus;
import com.bistro.booking.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {
    List<ReservationRequest> findAllByStatus(ReservationRequestStatus status, PageRequest of);

    Page<ReservationRequest> findAllByBookingRestaurantManagerId(Long managerId, Pageable pageable);

    Page<ReservationRequest> findAllByBookingRestaurantManagerIdAndStatus(Long managerId, ReservationRequestStatus status, Pageable pageable);
}
