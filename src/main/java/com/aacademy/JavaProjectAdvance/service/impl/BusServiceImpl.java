package com.aacademy.JavaProjectAdvance.service.impl;

import com.aacademy.JavaProjectAdvance.exception.DuplicateResourceException;
import com.aacademy.JavaProjectAdvance.exception.ResourceNotFoundException;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.repository.BusRepository;
import com.aacademy.JavaProjectAdvance.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    @Autowired
    public BusServiceImpl(BusRepository busRepository) {
        this.busRepository = busRepository;
    }


    @Override
    public Bus findById(Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Bus with Id: %d not exists", id)));
    }

    @Override
    public Bus findByNumber(String number) {
        return busRepository.findByNumber(number)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Bus with name: %s not exists", number)));
    }

    @Override
    public Bus save(Bus bus) {
        try {
            return busRepository.save(bus);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException(String.format("Bus with number %s already exists.", bus.getNumber()));
        }
    }

    @Override
    public Bus update(Bus bus, Long id) {
        Bus foundBus = findById(id);
        Bus updateBus = Bus.builder()
                .id(foundBus.getId())
                .number(bus.getNumber())
                .build();
        return save(updateBus);
    }

    @Override
    public void delete(Long id) {
        busRepository.deleteById(id);
    }

    @Override
    public Set<Bus> findAll() {
        return new HashSet<>(busRepository.findAll());
    }
}
