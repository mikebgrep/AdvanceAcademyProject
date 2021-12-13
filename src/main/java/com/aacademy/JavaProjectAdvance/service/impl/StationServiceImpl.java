package com.aacademy.JavaProjectAdvance.service.impl;

import com.aacademy.JavaProjectAdvance.exception.ResourceNotFoundException;
import com.aacademy.JavaProjectAdvance.model.Station;
import com.aacademy.JavaProjectAdvance.repository.StationRepository;
import com.aacademy.JavaProjectAdvance.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
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
                .build();

        return save(updatedStation);
    }

    @Override
    public Station save(Station station) {
        return stationRepository.save(station);
    }
}
