package com.aacademy.JavaProjectAdvance.service.imp.func;

import com.aacademy.JavaProjectAdvance.exception.DuplicateResourceException;
import com.aacademy.JavaProjectAdvance.exception.ResourceNotFoundException;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.model.Driver;
import com.aacademy.JavaProjectAdvance.repository.BusRepository;
import com.aacademy.JavaProjectAdvance.repository.DriverRepository;
import com.aacademy.JavaProjectAdvance.service.DriverService;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DriverServiceFuncTest {

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BusRepository busRepository;


    @Test
    public void verifyFindById() {
        Driver driver = Driver.builder()
                .driverNumber("123213")
                .bus(busRepository.save(Bus.builder().number("12a").build()))
                .build();

        Driver expected = driverRepository.save(driver);
        Driver actual = driverService.findById(expected.getId());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDriverNumber(), actual.getDriverNumber());

    }

    @Test
    public void verifyByDriverNumber() {
        Driver savedDriver = driverRepository.save(Driver.builder()
                .driverNumber("123456")
                .bus(busRepository.save(Bus.builder().number("12a").build()))
                .build());
        Driver actual = driverService.findByDriverNumber(savedDriver.getDriverNumber());

        assertEquals(actual.getDriverNumber(), savedDriver.getDriverNumber());
    }

    @Test
    public void verifyFindAll() {
        driverRepository.saveAll(Arrays.asList(
                Driver.builder().driverNumber("1234516").bus(busRepository.save(Bus.builder().number("12a").build())).build(),
                Driver.builder().driverNumber("1234562").bus(busRepository.save(Bus.builder().number("12").build())).build()
        ));
        Set<Driver> actual = driverService.findAll();
        assertThat(actual.size(), is(2));
    }

    @Test
    public void verifyUpdate() {
        Driver driver = Driver.builder().driverNumber("123456").bus(busRepository.save(Bus.builder().number("12a").build())).build();
        Driver savedDriver = driverRepository.save(driver);

        Driver expected = Driver.builder()
                .id(savedDriver.getId())
                .driverNumber("12345561")
                .bus(busRepository.save(Bus.builder().number("12").build()))
                .build();

        Driver actual = driverService.update(expected, savedDriver.getId());

        assertThat(expected.getId(), is(actual.getId()));
        assertThat(expected.getDriverNumber(), is(actual.getDriverNumber()));
    }

    @Test
    public void verifySave() {
        Driver savedDriver = driverService.save(Driver.builder()
                .driverNumber("546456")
                .bus(busRepository.save(Bus.builder().number("12a").build()))
                .build());
        Optional<Driver> actualDriver = driverRepository.findById(savedDriver.getId());
        assertTrue(actualDriver.isPresent());
    }

    @Test
    public void verifyDeleteById() {
        Driver savedDriver = driverRepository.save(Driver.builder()
                .driverNumber("123455")
                .bus(busRepository.save(Bus.builder().number("12a").build()))
                .build());
        driverService.delete(savedDriver.getId());
        List<Driver> actual = driverRepository.findAll();

        assertThat(actual.size(), is(0));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void verifyFindByDriverNumberException() {
        driverService.findByDriverNumber("1234");
    }

    @Test(expected = DuplicateResourceException.class)
    public void verifySaveDuplicateException() {
        driverService.save(Driver.builder().driverNumber("123").build());
        driverService.save(Driver.builder().driverNumber("123").build());
    }
}
