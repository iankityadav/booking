package com.bistro.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ReservationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @JsonIgnore
    @OneToOne(mappedBy = "reservationRequest")
    private Booking booking;

    @Enumerated(EnumType.STRING)
    private ReservationRequestStatus status;

    @CreatedDate
    private LocalDateTime createdAt;
}

