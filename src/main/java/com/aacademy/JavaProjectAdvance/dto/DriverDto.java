package com.aacademy.JavaProjectAdvance.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DriverDto {

    private Long id;

    private String driverNumber;

    private Long busId;
}
