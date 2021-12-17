package com.aacademy.JavaProjectAdvance.controller.integration;

import com.aacademy.JavaProjectAdvance.dto.StationDto;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.model.Station;
import com.aacademy.JavaProjectAdvance.repository.BusRepository;
import com.aacademy.JavaProjectAdvance.repository.StationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StationControllerIntTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private BusRepository busRepository;


    @Test
    public void saveStation() throws JsonProcessingException {
        Bus bus = busRepository.save(Bus.builder().number("21").build());
        Station station = stationRepository.save(Station.builder().id(1L).name("Levski").buses(Set.of(bus)).build());
        String reqJson = objectMapper.writeValueAsString(StationDto.builder().id(station.getId()).name(station.getName())
                .busesIds(station.getBuses().stream().map(Bus::getId).collect(Collectors.toSet())).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .post("http://localhost:8080/stations")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Levski"));
    }

    @Test
    public void updateStation() throws JsonProcessingException {
        Bus bus = busRepository.save(Bus.builder().number("21").build());
        Station station = stationRepository.save(Station.builder().id(1L).name("Levski").buses(Set.of(bus)).build());
        String reqJson = objectMapper.writeValueAsString(StationDto.builder().id(station.getId()).name("Center")
                .busesIds(station.getBuses().stream().map(Bus::getId).collect(Collectors.toSet())).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .put("http://localhost:8080/stations/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Center"));
    }

    @Test
    public void deleteStation() throws JsonProcessingException {
        Bus bus = busRepository.save(Bus.builder().number("21").build());
        Station station = stationRepository.save(Station.builder().id(1L).name("Levski").buses(Set.of(bus)).build());
        String reqJson = objectMapper.writeValueAsString(StationDto.builder().id(station.getId()).name(station.getName())
                .busesIds(station.getBuses().stream().map(Bus::getId).collect(Collectors.toSet())).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .delete("http://localhost:8080/stations/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void findByStationName() throws JsonProcessingException {
        Bus bus = busRepository.save(Bus.builder().number("21").build());
        Station station = stationRepository.save(Station.builder().id(1L).name("Levski").buses(Set.of(bus)).build());
        String reqJson = objectMapper.writeValueAsString(StationDto.builder().id(station.getId()).name(station.getName())
                .busesIds(station.getBuses().stream().map(Bus::getId).collect(Collectors.toSet())).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .get("http://localhost:8080/stations/name/Levski")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Levski"));
    }

    @Test
    public void findByStationId() throws JsonProcessingException {
        Bus bus = busRepository.save(Bus.builder().number("21").build());
        Station station = stationRepository.save(Station.builder().id(1L).name("Levski").buses(Set.of(bus)).build());
        String reqJson = objectMapper.writeValueAsString(StationDto.builder().id(station.getId()).name(station.getName())
                .busesIds(station.getBuses().stream().map(Bus::getId).collect(Collectors.toSet())).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .get("http://localhost:8080/stations/id/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Levski"));
    }

    @Test
    public void findAllStations() throws JsonProcessingException {
        Bus bus = busRepository.save(Bus.builder().number("21").build());
        Station station = stationRepository.save(Station.builder().id(1L).name("Levski").buses(Set.of(bus)).build());
        String reqJson = objectMapper.writeValueAsString(StationDto.builder().id(station.getId()).name(station.getName())
                .busesIds(station.getBuses().stream().map(Bus::getId).collect(Collectors.toSet())).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .get("http://localhost:8080/stations")
                .then()
                .statusCode(200)
                .body("id", hasItem(1))
                .body("name", hasItem("Levski"));
    }
}
