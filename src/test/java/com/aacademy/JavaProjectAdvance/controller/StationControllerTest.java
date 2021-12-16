package com.aacademy.JavaProjectAdvance.controller;

import com.aacademy.JavaProjectAdvance.converter.StationConverter;
import com.aacademy.JavaProjectAdvance.dto.StationDetachBusDto;
import com.aacademy.JavaProjectAdvance.dto.StationDto;
import com.aacademy.JavaProjectAdvance.model.Station;
import com.aacademy.JavaProjectAdvance.service.StationService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = StationController.class)
public class StationControllerTest extends BaseControllerTest {

    @MockBean
    private StationService stationService;

    @MockBean
    private StationConverter stationConverter;

    @Test
    public void save() throws Exception {
        StationDto stationDto = StationDto.builder().id(1L).name("Levski").build();
        String reqJson = objectMapper.writeValueAsString(stationDto);

        when(stationConverter.toStation(any(StationDto.class))).thenReturn(Station.builder().build());
        when(stationService.save(any(Station.class))).thenReturn(Station.builder().build());
        when(stationConverter.toStationDto(any(Station.class))).thenReturn(StationDto.builder().id(1L).name("Levski").build());

        mockMvc.perform(post("/stations")
                        .content(reqJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Levski")));
    }

    @Test
    public void update() throws Exception {
        StationDto stationDto = StationDto.builder().id(1L).name("Levski").build();
        String reqJson = objectMapper.writeValueAsString(stationDto);

        when(stationConverter.toStationDto(any())).thenReturn(stationDto);

        mockMvc.perform(put("/stations/1")
                        .content(reqJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Levski")));

    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/stations/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {

        when(stationService.findById(any(Long.class))).thenReturn(Station.builder().build());
        when(stationConverter.toStationDto(any(Station.class))).thenReturn(StationDto.builder().id(1L).build());

        mockMvc.perform(get("/stations/id/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void findByName() throws Exception {
        when(stationService.findByName(any(String.class))).thenReturn(Station.builder().build());
        when(stationConverter.toStationDto(any(Station.class))).thenReturn(StationDto.builder().id(1L).name("Center").build());

        mockMvc.perform(get("/stations/name/Center"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Center")));
    }

    @Test
    public void findAll() throws Exception {
        when(stationService.findAll()).thenReturn(Set.of(Station.builder().build()));
        when(stationConverter.toStationDto(any(Station.class))).thenReturn(StationDto.builder().id(1L).build());

        mockMvc.perform(get("/stations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

}