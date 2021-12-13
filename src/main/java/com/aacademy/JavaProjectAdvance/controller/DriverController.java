package com.aacademy.JavaProjectAdvance.controller;

import com.aacademy.JavaProjectAdvance.converter.DriverConverter;
import com.aacademy.JavaProjectAdvance.dto.DriverDto;
import com.aacademy.JavaProjectAdvance.model.Driver;
import com.aacademy.JavaProjectAdvance.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/drivers")
public class DriverController {

    private final DriverService driverService;
    private final DriverConverter driverConverter;

    @Autowired
    public DriverController(DriverService driverService, DriverConverter driverConverter) {
        this.driverService = driverService;
        this.driverConverter = driverConverter;
    }


    @Autowired

    @GetMapping
    public ResponseEntity<Set<DriverDto>> findAll() {
        return ResponseEntity.ok(driverService.findAll()
                .stream()
                .map(driverConverter::toDriverDto)
                .collect(Collectors.toSet()));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<DriverDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(driverConverter.toDriverDto(driverService.findById(id)));
    }

    @GetMapping(value = "/number/{number}")
    public ResponseEntity<DriverDto> findByDriverNumber(@PathVariable String driverNumber) {
        return ResponseEntity.ok(driverConverter.toDriverDto(driverService.findByDriverNumber(driverNumber)));
    }

    @PostMapping
    public ResponseEntity<DriverDto> save(@RequestBody @Valid DriverDto driverDto) {
        Driver driver = driverConverter.toDriver(driverDto);
        Driver savedDriver = driverService.save(driver);
        return ResponseEntity.ok(driverConverter.toDriverDto(savedDriver));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DriverDto> update(@RequestBody @Valid DriverDto driverDto, @PathVariable Long id) {
        Driver driver = driverConverter.toDriver(driverDto);
        Driver updatedDriver = driverService.update(driver, id);
        return ResponseEntity.ok(driverConverter.toDriverDto(updatedDriver));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DriverDto> delete(@PathVariable(value = "id") Long id) {
        driverService.delete(id);
        return ResponseEntity.ok().build();
    }
}
