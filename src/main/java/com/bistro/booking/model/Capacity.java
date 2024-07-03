package com.bistro.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
public class Capacity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(2)
    @Max(20)
    private int tableType;

    private int numberOfTables;

    @JsonIgnore
    @OneToOne(mappedBy = "capacity")
    private Restaurant restaurant;
}
