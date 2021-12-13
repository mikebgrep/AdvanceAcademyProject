package com.aacademy.JavaProjectAdvance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drivers",
        indexes = { @Index(name = "uidx_driver_number", columnList = "driverNumber", unique = true),

})
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 15)
    private String driverNumber;

    @ManyToOne
    @JoinColumn(name="bus_id", nullable = false)
    private Bus bus;

}
