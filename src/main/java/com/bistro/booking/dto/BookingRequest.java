package com.bistro.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingRequest {
    private LocalDateTime date;
    private int numberOfGuests;
    private String timeSlot;
    private Long restaurantId;
    private Long userId;
}
