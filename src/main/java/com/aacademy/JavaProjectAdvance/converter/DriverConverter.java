package com.aacademy.JavaProjectAdvance.converter;

import com.aacademy.JavaProjectAdvance.dto.DriverDto;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.model.Driver;
import org.springframework.stereotype.Component;

@Component
public class DriverConverter {

    public DriverDto toDriverDto(Driver driver) {
        return DriverDto.builder()
                .id(driver.getId())
                .driverNumber(driver.getDriverNumber())
                .busId(driver.getBus().getId())
                .build();
    }

    public Driver toDriver(DriverDto driverDto) {
        return Driver.builder()
                .id(driverDto.getId())
                .driverNumber(driverDto.getDriverNumber())
                .bus(Bus.builder().id(driverDto.getBusId()).build())
                .build();
    }


}
