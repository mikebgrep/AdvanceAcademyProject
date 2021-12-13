package com.aacademy.JavaProjectAdvance.converter;

import com.aacademy.JavaProjectAdvance.dto.BusDto;
import com.aacademy.JavaProjectAdvance.dto.StationDto;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.model.Station;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StationConverter {

    public StationDto toStationDto(Station station) {
        return StationDto.builder()
                .id(station.getId())
                .name(station.getName())
                .busesIds(station.getBuses()
                        .stream()
                        .map(Bus::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

    public Station toStation(StationDto stationDto) {
        return Station.builder()
                .id(stationDto.getId())
                .name(stationDto.getName())
                .buses(stationDto.getBusesIds()
                        .stream()
                        .map((busId) ->
                                Bus.builder().id(busId).build())
                        .collect(Collectors.toSet()))
                .build();
    }


}
