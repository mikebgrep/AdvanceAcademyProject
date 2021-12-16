package com.aacademy.JavaProjectAdvance.controller;

import com.aacademy.JavaProjectAdvance.converter.BusConverter;
import com.aacademy.JavaProjectAdvance.dto.BusDto;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.service.BusService;
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

@WebMvcTest(value = BusController.class)
public class BusControllerTest extends BaseControllerTest {

    @MockBean
    private BusService busService;

    @MockBean
    private BusConverter busConverter;

    @Test
    public void save() throws Exception {
        BusDto busDto = BusDto.builder().number("12a").build();
        String reqJson = objectMapper.writeValueAsString(busDto);

        when(busConverter.toBus(any(BusDto.class))).thenReturn(Bus.builder().build());
        when(busService.save(any(Bus.class))).thenReturn(Bus.builder().build());
        when(busConverter.toBusDto(any(Bus.class))).thenReturn(BusDto.builder().id(1L).number("12a").build());

        mockMvc.perform(post("/buses")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.number", is("12a")));
    }

    @Test
    public void update() throws Exception {
        BusDto busDto = BusDto.builder().id(1L).number("12a").build();
        String reqJson = objectMapper.writeValueAsString(busDto);
        when(busConverter.toBusDto(any())).thenReturn(busDto);

        mockMvc.perform(put("/buses/1")
                .content(reqJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.number", is("12")));
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/buses/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByNumber() throws Exception {
        when(busService.findByNumber(any(String.class))).thenReturn(Bus.builder().build());
        when(busConverter.toBusDto(any(Bus.class))).thenReturn(BusDto.builder().id(1L).number("21").build());

        mockMvc.perform(get("/buses/number/21"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.number", is("21")));
    }

    @Test
    public void findById() throws Exception {
        when(busService.findById(any(Long.class))).thenReturn(Bus.builder().build());
        when(busConverter.toBusDto(any(Bus.class))).thenReturn(BusDto.builder().id(1L).build());

        mockMvc.perform(get("/buses/id/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void findAll() throws Exception {
        when(busService.findAll()).thenReturn(Set.of(Bus.builder().build()
                , Bus.builder().build()));
        when(busConverter.toBusDto(any(Bus.class))).thenReturn(BusDto.builder().id(1L).build());

        mockMvc.perform(get("/buses"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));
    }

}
