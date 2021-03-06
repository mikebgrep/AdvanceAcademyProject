package com.aacademy.JavaProjectAdvance.service.impl;

import com.aacademy.JavaProjectAdvance.exception.DuplicateResourceException;
import com.aacademy.JavaProjectAdvance.exception.ResourceNotFoundException;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.model.Station;
import com.aacademy.JavaProjectAdvance.repository.StationRepository;
import com.aacademy.JavaProjectAdvance.service.BusService;
import com.aacademy.JavaProjectAdvance.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final BusService busService;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository, BusService busService) {
        this.stationRepository = stationRepository;
        this.busService = busService;
    }


    @Override
    public Station findById(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Station with name: %d not exists", id)));
    }

    @Override
    public Station findByName(String name) {
        return stationRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Station with name: %s not exists", name)));
    }

    @Override
    public Set<Station> findAll() {
        return new HashSet<>(stationRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        stationRepository.deleteById(id);
    }

    @Override
    public Station update(Station station, Long id) {
        Station foundStation = findById(id);
        Station updatedStation = Station.builder()
                .id(foundStation.getId())
                .name(station.getName())
                .buses(station.getBuses())
                .build();

        return stationRepository.save(updatedStation);
    }

    @Override
    public Station save(Station station) {
        try {
            return stationRepository.save(station);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException(String.format("Station with that name %S already exists.", station.getName()));
        }


    }

    @Override
    public void detachStationBus(Long stationId, Set<Long> busesIds) {
        Station foundStation = findById(stationId);
        foundStation.getBuses()
                .removeIf(bus -> busesIds.contains(bus.getId()));

        stationRepository.save(foundStation);
    }
}
