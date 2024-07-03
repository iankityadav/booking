package com.bistro.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RestaurantTableInfo {

    private String tableType;
    private int numberOfTables;
    private LocalDateTime createdAt;

}
