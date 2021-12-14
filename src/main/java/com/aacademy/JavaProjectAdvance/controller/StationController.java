package com.aacademy.JavaProjectAdvance.controller;

import com.aacademy.JavaProjectAdvance.converter.StationConverter;
import com.aacademy.JavaProjectAdvance.dto.StationDetachBus;
import com.aacademy.JavaProjectAdvance.dto.StationDto;
import com.aacademy.JavaProjectAdvance.model.Station;
import com.aacademy.JavaProjectAdvance.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/stations")
public class StationController {

    private final StationService stationService;
    private final StationConverter stationConverter;

    @Autowired
    public StationController(StationService stationService, StationConverter stationConverter) {
        this.stationService = stationService;
        this.stationConverter = stationConverter;
    }

    @GetMapping()
    public ResponseEntity<Set<StationDto>> findAll() {
        return ResponseEntity.ok(stationService.findAll()
                .stream()
                .map(stationConverter::toStationDto)
                .collect(Collectors.toSet()));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<StationDto> findById(@PathVariable Long id) {

        return ResponseEntity.ok(stationConverter
                .toStationDto(stationService.findById(id)));
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<StationDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(stationConverter.toStationDto(stationService.findByName(name)));
    }

    @PostMapping
    public ResponseEntity<StationDto> save(@RequestBody @Valid StationDto stationDto) {
        Station station = stationConverter.toStation(stationDto);
        Station savedStation = stationService.save(station);
        return ResponseEntity.ok(stationConverter.toStationDto(savedStation));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StationDto> update(@RequestBody @Valid StationDto stationDto, @RequestBody @Valid Long id) {
        Station station = stationConverter.toStation(stationDto);
        Station updatedStation = stationService.update(station, id);
        return ResponseEntity.ok(stationConverter.toStationDto(stationService.save(updatedStation)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StationDto> delete(@PathVariable(value = "{id}") Long id) {
        stationService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/detach")
    public ResponseEntity<HttpStatus> detach(@RequestBody StationDetachBus stationDetachBus) {
        stationService.detachStationBus(stationDetachBus.getId(), stationDetachBus.getBusesIds());
        return ResponseEntity.ok().build();
    }
}
