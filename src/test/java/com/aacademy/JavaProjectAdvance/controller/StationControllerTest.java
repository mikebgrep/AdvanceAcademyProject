package com.aacademy.JavaProjectAdvance.controller;

import com.aacademy.JavaProjectAdvance.converter.StationConverter;
import com.aacademy.JavaProjectAdvance.service.StationService;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = DriverController.class)

public class StationControllerTest extends BaseControllerTest{

    @MockBean
    private StationService stationService;

    @MockBean
    private StationConverter stationConverter;

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/stations/1"))
                .andExpect(status().isOk());
    }
}
