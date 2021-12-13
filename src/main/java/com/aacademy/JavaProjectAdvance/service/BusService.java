package com.aacademy.JavaProjectAdvance.service;

import com.aacademy.JavaProjectAdvance.model.Bus;

import java.util.Set;

public interface BusService {

    Bus findById(Long id);

    Bus findByNumber(String number);

    Bus save(Bus bus);

    Bus update(Bus bus, Long id);

    void delete(Long id);

    Set<Bus> findAll();


}
