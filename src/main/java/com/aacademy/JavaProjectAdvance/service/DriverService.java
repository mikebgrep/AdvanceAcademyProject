package com.aacademy.JavaProjectAdvance.service;

import com.aacademy.JavaProjectAdvance.model.Driver;

import java.util.Set;

public interface DriverService {

    Driver findById(Long id);

    Driver findByDriverNumber(String driverNumber);

    Driver save(Driver driver);

    Driver update(Driver driver, Long id);

    void delete(Long id);

    Set<Driver> findAll();
}
