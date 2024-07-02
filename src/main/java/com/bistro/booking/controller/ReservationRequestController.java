package com.bistro.booking.controller;

import com.bistro.booking.model.ReservationRequest;
import com.bistro.booking.model.ReservationRequestStatus;
import com.bistro.booking.service.ReservationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservation-requests")
public class ReservationRequestController {
    @Autowired
    private ReservationRequestService reservationRequestService;

    @GetMapping
    public ResponseEntity<List<ReservationRequest>> getAllReservationRequests(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam ReservationRequestStatus status) {
        return ResponseEntity.ok(reservationRequestService.findAllByStatus(page, size, status));
    }
}
