package com.bistro.booking.service;

import com.bistro.booking.model.ReservationRequest;
import com.bistro.booking.model.ReservationRequestStatus;
import com.bistro.booking.repository.ReservationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationRequestService {

    @Autowired
    private ReservationRequestRepository reservationRequestRepository;

    public List<ReservationRequest> findAllByStatus(int page, int size, ReservationRequestStatus status) {
        return reservationRequestRepository.findAllByStatus(status, PageRequest.of(page, size));
    }
    public Page<ReservationRequest> getAllReservationRequestsForManager(Long managerId, Pageable pageable) {
        return reservationRequestRepository.findAllByBookingRestaurantManagerId(managerId, pageable);
    }

    public Page<ReservationRequest> getAllReservationRequestsForManager(Long managerId, ReservationRequestStatus status, Pageable pageable) {
        return reservationRequestRepository.findAllByBookingRestaurantManagerIdAndStatus(managerId, status, pageable);
    }

    public ReservationRequest updateReservationRequestStatus(Long requestId, ReservationRequestStatus status) {
        ReservationRequest request = reservationRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(status);
        return reservationRequestRepository.save(request);
    }
}

