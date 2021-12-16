package com.aacademy.JavaProjectAdvance.controller;

import com.aacademy.JavaProjectAdvance.converter.DriverConverter;
import com.aacademy.JavaProjectAdvance.dto.DriverDto;
import com.aacademy.JavaProjectAdvance.model.Driver;
import com.aacademy.JavaProjectAdvance.service.DriverService;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = DriverController.class)
public class DriverControllerTest extends BaseControllerTest {

    @MockBean
    private DriverService driverService;

    @MockBean
    private DriverConverter driverConverter;

    @Test
    public void save() throws Exception {
        DriverDto driverDto = DriverDto.builder().driverNumber("12345").build();
        String reqJson = objectMapper.writeValueAsString(driverDto);

        when(driverConverter.toDriver(any(DriverDto.class))).thenReturn(Driver.builder().build());
        when(driverService.save(any(Driver.class))).thenReturn(Driver.builder().build());
        when(driverConverter.toDriverDto(any(Driver.class))).thenReturn(DriverDto.builder().id(1L).driverNumber("12345").build());

        mockMvc.perform(post("/drivers")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.driverNumber", is("12345")));
    }

    @Test
    public void update() throws Exception {
        DriverDto driverDto = DriverDto.builder().id(1L).driverNumber("12345").build();
        String reqJson = objectMapper.writeValueAsString(driverDto);

        when(driverConverter.toDriverDto(any())).thenReturn(driverDto);

        mockMvc.perform(put("/drivers/1")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.driverNumber", is("12345")));
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/drivers/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        when(driverService.findById(any(Long.class))).thenReturn(Driver.builder().build());
        when(driverConverter.toDriverDto(any(Driver.class))).thenReturn(DriverDto.builder().id(1L).driverNumber("12345").build());

        mockMvc.perform(get("/drivers/id/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.driverNumber", is("12345")));
    }

    @Test
    public void findByDriverNumber() throws Exception {
        when(driverService.findByDriverNumber(any(String.class))).thenReturn(Driver.builder().build());
        when(driverConverter.toDriverDto(any(Driver.class))).thenReturn(DriverDto.builder().id(1L).driverNumber("12345").build());

        mockMvc.perform(get("/drivers/number/12345"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.driverNumber", is("12345")));
    }

    @Test
    public void findAll() throws Exception {
        when(driverService.findAll()).thenReturn(Set.of(
                Driver.builder().build(),
                Driver.builder().build()));
        when(driverConverter.toDriverDto(any(Driver.class))).thenReturn(DriverDto.builder().id(1L).driverNumber("12345").build());

        mockMvc.perform(get("/drivers/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].driverNumber", is("12345")));
    }




}
