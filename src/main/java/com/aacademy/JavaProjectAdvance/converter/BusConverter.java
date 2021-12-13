package com.aacademy.JavaProjectAdvance.converter;

import com.aacademy.JavaProjectAdvance.dto.BusDto;
import com.aacademy.JavaProjectAdvance.model.Bus;
import org.springframework.stereotype.Component;

@Component
public class BusConverter {

    public BusDto toBusDto(Bus bus){
        return BusDto.builder()
                .id(bus.getId())
                .number(bus.getNumber())
                .build();
    }

    public Bus toBus(BusDto busDto) {
        return Bus.builder()
                .id(busDto.getId())
                .number(busDto.getNumber())
                .build();
    }

}
