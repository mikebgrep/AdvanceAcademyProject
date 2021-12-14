package com.aacademy.JavaProjectAdvance.service;

import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.model.Station;

import java.util.Set;

public interface StationService {

    Station findById(Long id);

    Station findByName(String name);

    Set<Station> findAll();

    void delete(Long id);

    Station update(Station station, Long id);

    Station save(Station station);

    void detachStationBus(Long stationId, Set<Long> busesIds);
}
