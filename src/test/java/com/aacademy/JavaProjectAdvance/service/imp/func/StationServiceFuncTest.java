package com.aacademy.JavaProjectAdvance.service.imp.func;

import com.aacademy.JavaProjectAdvance.exception.DuplicateResourceException;
import com.aacademy.JavaProjectAdvance.exception.ResourceNotFoundException;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.model.Station;
import com.aacademy.JavaProjectAdvance.repository.BusRepository;
import com.aacademy.JavaProjectAdvance.repository.StationRepository;
import com.aacademy.JavaProjectAdvance.service.BusService;
import com.aacademy.JavaProjectAdvance.service.StationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StationServiceFuncTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationService stationService;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusService busService;

    @Test
    public void verifyFindAll() {
        stationRepository.saveAll(Arrays.asList(
                Station.builder().name("Levski").build(),
                Station.builder().name("Centyr").build()
        ));
        Set<Station> actual = stationService.findAll();

        assertThat(actual.size(), is(2));
    }

    @Test
    public void verifyFindByName() {
        Station expected = stationRepository.save(Station.builder().name("Levski").build());
        Station actual = stationService.findByName(expected.getName());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void verifyFindById() {
        Station expected = stationRepository.save(Station.builder().name("Levski").build());
        Station actual = stationService.findById(expected.getId());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void verifySave() {
        Station savedStation = stationService.save(Station.builder().name("Levski").build());
        Optional<Station> actual = stationRepository.findById(savedStation.getId());

        assertTrue(actual.isPresent());
    }

    @Test
    public void verifyUpdate() {
        Station station = stationRepository.save(Station.builder().name("Levski").build());
        Station excepted = Station.builder()
                .id(station.getId())
                .name("Centyr")
                .build();
        Station actual = stationService.update(excepted, station.getId());

        assertThat(excepted.getId(), is(actual.getId()));
        assertThat(excepted.getName(), is(actual.getName()));
    }

    @Test
    public void verifyDeleteById() {
        Station savedStation = stationRepository.save(Station.builder().name("Levski").build());
        stationService.delete(savedStation.getId());
        List<Station> actual = stationRepository.findAll();

        assertThat(actual.size(), is(0));
    }

    @Test
    public void verifyDetachStationBus() {
        List<Bus> buses = busRepository.saveAll(Arrays.asList(
                Bus.builder().number("128a").build(),
                Bus.builder().number("21").build()));

        busService.findAll();

        Station actual = stationRepository.save(Station.builder()
                .name("Levski")
                .buses(new HashSet<>(buses))
                .build());
        stationService.detachStationBus(actual.getId(), buses.stream().map(Bus::getId).collect(Collectors.toSet()));

        assertThat(actual.getBuses().size(), is(0));
    }

    @Test(expected = DuplicateResourceException.class)
    public void verifyDuplicateException() {
        stationService.save(Station.builder().name("Levski").build());
        stationService.save(Station.builder().name("Levski").build());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void verifyNotFoundException() {
        stationService.findByName("Levski");
    }

}
