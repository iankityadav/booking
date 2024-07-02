package com.bistro.booking.controller;

import com.bistro.booking.dto.BookingRequest;
import com.bistro.booking.model.Booking;
import com.bistro.booking.service.BookingService;
import com.bistro.booking.service.ReservationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ReservationRequestService reservationRequestService;

    @PostMapping
    public ResponseEntity<Booking> bookTable(@RequestBody BookingRequest bookingRequest) {
        Booking savedBooking = bookingService.bookTable(bookingRequest);
        return ResponseEntity.ok(savedBooking);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<Booking>> getAllBookingsForCustomer(
            @PathVariable Long customerId,
            @RequestParam int page,
            @RequestParam int size) {
        Page<Booking> bookings = bookingService.getAllBookingsForCustomer(customerId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
