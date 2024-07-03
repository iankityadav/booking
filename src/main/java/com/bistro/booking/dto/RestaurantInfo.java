package com.bistro.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class RestaurantInfo {

    private String name;
    private String cuisines;
    private String location;
    private String workingDays;
    private String workingHour;
    private LocalDateTime createdAt;
    private String timeSlotInterval;
    private RestaurantTableInfo restaurantTableInfo;


}
