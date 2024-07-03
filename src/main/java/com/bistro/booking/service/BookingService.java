package com.bistro.booking.service;

import com.bistro.booking.dto.BookingRequest;
import com.bistro.booking.model.Booking;
import com.bistro.booking.model.BookingStatus;
import com.bistro.booking.model.ReservationRequest;
import com.bistro.booking.model.ReservationRequestStatus;
import com.bistro.booking.repository.BookingRepository;
import com.bistro.booking.repository.ReservationRequestRepository;
import com.bistro.booking.repository.RestaurantRepository;
import com.bistro.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRequestRepository reservationRequestRepository;

    public Booking bookTable(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setRestaurant(restaurantRepository.findAllById(Collections.singleton(bookingRequest.getRestaurantId())).get(0));
        booking.setStatus(BookingStatus.PENDING);
        booking.setCustomer(userRepository.findById(bookingRequest.getUserId()).get());
        booking.setTimeSlot(bookingRequest.getTimeSlot());
        booking.setNumberOfGuests(bookingRequest.getNumberOfGuests());
        booking.setDate(bookingRequest.getDate());
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setStatus(ReservationRequestStatus.PENDING);
        reservationRequest.setBooking(booking);
        booking.setReservationRequest(reservationRequest);
        Booking createdBooking = bookingRepository.save(booking);
        System.out.println("createdBooking = " + createdBooking.getId());
        System.out.println("reservationRequest = " + reservationRequest.getRequestId());
        return createdBooking;
    }

    public Page<Booking> getAllBookingsForCustomer(Long customerId, Pageable pageable) {
        return bookingRepository.findAllByCustomerId(customerId, pageable);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
