package com.aacademy.JavaProjectAdvance.service.imp.func;

import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.repository.BusRepository;
import com.aacademy.JavaProjectAdvance.service.BusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


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

}
