package com.bistro.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Users manager;

    private String name;

    private String cuisines;

    private String location;

    private String workingDays;

    private String workingHours;

    private String timeSlotInterval;

    @CreatedDate
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private Set<RestaurantTable> tables;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<Booking> bookings;
}

