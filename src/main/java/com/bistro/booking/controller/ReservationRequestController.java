package com.bistro.booking.controller;

import com.bistro.booking.model.BookingStatus;
import com.bistro.booking.model.ReservationRequest;
import com.bistro.booking.model.ReservationRequestStatus;
import com.bistro.booking.service.ReservationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation-requests")
public class ReservationRequestController {
    @Autowired
    private ReservationRequestService reservationRequestService;

    @GetMapping
    public ResponseEntity<List<ReservationRequest>> getAllReservationRequests(@RequestParam int page, @RequestParam int size, @RequestParam ReservationRequestStatus status) {
        return ResponseEntity.ok(reservationRequestService.findAllByStatus(page, size, status));
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<Page<ReservationRequest>> getAllReservationRequestsForManager(@PathVariable Long managerId, @RequestParam int page, @RequestParam int size, @RequestParam(required = false) ReservationRequestStatus status) {
        Page<ReservationRequest> requests;
        if (status != null) {
            requests = reservationRequestService.getAllReservationRequestsForManager(managerId, status, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "status").and(Sort.by(Sort.Direction.DESC, "createdAt"))));
        } else {
            requests = reservationRequestService.getAllReservationRequestsForManager(managerId, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "status").and(Sort.by(Sort.Direction.DESC, "createdAt"))));
        }
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{requestId}/confirm")
    public ResponseEntity<ReservationRequest> confirmReservationRequest(@PathVariable Long requestId) {
        ReservationRequest updatedRequest = reservationRequestService.updateReservationRequestStatus(requestId, ReservationRequestStatus.CONFIRMED, BookingStatus.CONFIRMED);
        return ResponseEntity.ok(updatedRequest);
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<ReservationRequest> rejectReservationRequest(@PathVariable Long requestId) {
        ReservationRequest updatedRequest = reservationRequestService.updateReservationRequestStatus(requestId, ReservationRequestStatus.REJECTED, BookingStatus.REJECTED);
        return ResponseEntity.ok(updatedRequest);
    }
}
