package com.bistro.booking.service;

import com.bistro.booking.model.ReservationRequest;
import com.bistro.booking.model.ReservationRequestStatus;
import com.bistro.booking.repository.ReservationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationRequestService {

    @Autowired
    private ReservationRequestRepository reservationRequestRepository;

    public List<ReservationRequest> findAllByStatus(int page, int size, ReservationRequestStatus status) {
        return reservationRequestRepository.findAllByStatus(status, PageRequest.of(page, size));
    }
}

