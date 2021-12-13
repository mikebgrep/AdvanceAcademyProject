package com.aacademy.JavaProjectAdvance.controller;


import com.aacademy.JavaProjectAdvance.converter.BusConverter;
import com.aacademy.JavaProjectAdvance.dto.BusDto;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/buses")
public class BusController {

    private final BusService busService;
    private final BusConverter busConverter;

    @Autowired
    public BusController(BusService busService, BusConverter busConverter) {
        this.busService = busService;
        this.busConverter = busConverter;
    }

    @GetMapping
    public ResponseEntity<Set<BusDto>> findAll() {
        return ResponseEntity.ok(busService.findAll()
                .stream()
                .map(busConverter::toBusDto)
                .collect(Collectors.toSet()));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<BusDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(busConverter.toBusDto(busService.findById(id)));
    }

    @GetMapping(value = "/number/{number}")
    public ResponseEntity<BusDto> findByNumber(@PathVariable String number) {
        return ResponseEntity.ok(busConverter.toBusDto(busService.findByNumber(number)));
    }

    @PostMapping
    public ResponseEntity<BusDto> save(@RequestBody @Valid BusDto busDto) {
        Bus  bus = busConverter.toBus(busDto);
        Bus savedBus = busService.save(bus);
        return ResponseEntity.ok(busConverter.toBusDto(savedBus));
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<BusDto> update(@RequestBody @Valid BusDto busDto, @PathVariable Long id) {
        Bus bus = busConverter.toBus(busDto);
        Bus updatedBus = busService.update(bus, id);
        return ResponseEntity.ok(busConverter.toBusDto(updatedBus));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(value = "id") Long id) {
        busService.delete(id);
        return ResponseEntity.ok().build();
    }






}
