package com.aacademy.JavaProjectAdvance.service.imp.func;

import com.aacademy.JavaProjectAdvance.exception.DuplicateResourceException;
import com.aacademy.JavaProjectAdvance.exception.ResourceNotFoundException;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.model.Driver;
import com.aacademy.JavaProjectAdvance.repository.BusRepository;
import com.aacademy.JavaProjectAdvance.service.BusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BusServiceFuncTest {

    @Autowired
    private BusService busService;

    @Autowired
    private BusRepository busRepository;

    @Test
    public void verifyUpdate() {
        Bus bus = Bus.builder()
                .number("21")
                .build();

        Bus saveBus = busRepository.save(bus);
        Bus expected = Bus.builder()
                .id(saveBus.getId())
                .number("21a")
                .build();

        Bus actual = busService.update(expected, saveBus.getId());
        assertThat(expected.getId(), is(actual.getId()));
        assertThat(expected.getNumber(), is(actual.getNumber()));
    }

    @Test
    public void verifyFindById() {
        Bus bus = Bus.builder().number("12a").build();

        Bus expected = busRepository.save(bus);
        Bus actual = busService.findById(expected.getId());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getNumber(), actual.getNumber());
    }

    @Test
    public void verifyFindByNumber() {
        Bus bus = Bus.builder().number("12a").build();

        Bus expected = busRepository.save(bus);
        Bus actual = busService.findByNumber(expected.getNumber());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getNumber(), actual.getNumber());
    }

    @Test
    public void verifyFindAll() {
        busRepository.saveAll(Arrays.asList(
                Bus.builder().number("12a").build(),
                Bus.builder().number("12").build()
        ));

        Set<Bus> actual = busService.findAll();
        assertThat(actual.size(), is(2));
    }

    @Test
    public void verifySave() {
        Bus savedBus = busService.save(Bus.builder().number("12a").build());
        Optional<Bus> actual = busRepository.findById(savedBus.getId());

        assertTrue(actual.isPresent());
    }

    @Test
    public void verifyDeleteById() {
        Bus savedBus = busRepository.save(Bus.builder().number("11").build());
        busService.delete(savedBus.getId());
        List<Bus> actual = busRepository.findAll();

        assertThat(actual.size(), is(0));
    }
    @Test(expected = ResourceNotFoundException.class)
    public void verifyFindByNumberException() {

        busService.findByNumber("128");
    }

    @Test(expected = DuplicateResourceException.class)
    public void verifySaveDuplicateException() {
        busService.save(Bus.builder().number("11").build());
        busService.save(Bus.builder().number("11").build());
    }

}
