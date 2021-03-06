package com.aacademy.JavaProjectAdvance.service.impl;

import com.aacademy.JavaProjectAdvance.exception.DuplicateResourceException;
import com.aacademy.JavaProjectAdvance.exception.ResourceNotFoundException;
import com.aacademy.JavaProjectAdvance.model.Driver;
import com.aacademy.JavaProjectAdvance.repository.DriverRepository;
import com.aacademy.JavaProjectAdvance.service.DriverService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;


    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver findById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Driver with that id %d not exists", id)));
    }

    @Override
    public Driver findByDriverNumber(String driverNumber) {
        return driverRepository.findByDriverNumber(driverNumber)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Driver with that number %s not exists", driverNumber)));
    }

    @Override
    public Driver save(Driver driver) {
        try {
            return driverRepository.save(driver);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException(String.format("Driver with number %s already exists.", driver.getDriverNumber()));
        }

    }

    @Override
    public Driver update(Driver driver, Long id) {
        Driver foundDriver = findById(id);
        Driver updatedDriver = Driver.builder()
                .id(foundDriver.getId())
                .driverNumber(driver.getDriverNumber())
                .bus(driver.getBus())
                .build();
        return driverRepository.save(updatedDriver);
    }


    @Override
    public void delete(Long id) {
        driverRepository.deleteById(id);
    }

    @Override
    public Set<Driver> findAll() {
        return new HashSet<>(driverRepository.findAll());
    }
}
