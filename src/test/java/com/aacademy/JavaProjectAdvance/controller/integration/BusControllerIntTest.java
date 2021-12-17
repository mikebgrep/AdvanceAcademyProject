package com.aacademy.JavaProjectAdvance.controller.integration;

import com.aacademy.JavaProjectAdvance.dto.BusDto;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.repository.BusRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BusControllerIntTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BusRepository busRepository;


    @Test
    public void saveBus() throws JsonProcessingException {

        BusDto busDto = BusDto.builder().number("21a").build();
        String reqJson = objectMapper.writeValueAsString(busDto);

        given()
                .contentType(ContentType.JSON.toString())
                .body(reqJson)
                .when()
                .post("http://localhost:8080/buses")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("number", equalTo("21a"));
    }

    @Test
    public void updateBus() throws JsonProcessingException {

        busRepository.save(Bus.builder().id(1L).number("21").build());
        BusDto busDto = BusDto.builder().id(1L).number("21a").build();

        String reqJson = objectMapper.writeValueAsString(busDto);

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .put("http://localhost:8080/buses/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("number", equalTo("21a"));
    }

    @Test
    public void deleteBus() throws JsonProcessingException {
        Bus bus = busRepository.save(Bus.builder().id(1L).number("148").build());
        String reqJson = objectMapper.writeValueAsString(BusDto.builder().id(bus.getId()).number(bus.getNumber()).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .delete("http://localhost:8080/buses/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void findBusById() throws JsonProcessingException {
        Bus bus = busRepository.save(Bus.builder().id(1L).number("21a").build());
        String reqJson = objectMapper.writeValueAsString(BusDto.builder().id(bus.getId()).number(bus.getNumber()).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .get("http://localhost:8080/buses/id/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("number", equalTo("21a"));
    }

    @Test
    public void findBusByNumber() throws JsonProcessingException {
        Bus bus = busRepository.save(Bus.builder().id(1L).number("21a").build());
        String reqJson = objectMapper.writeValueAsString(BusDto.builder().id(bus.getId()).number(bus.getNumber()).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .get("http://localhost:8080/buses/number/21a")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("number", equalTo("21a"));
    }

    @Test
    public void findAllBuses() throws JsonProcessingException {
        Bus bus = busRepository.save(Bus.builder().number("148").build());
        String reqJson = objectMapper.writeValueAsString(BusDto.builder().id(bus.getId()).number(bus.getNumber()).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .get("http://localhost:8080/buses")
                .then()
                .statusCode(200)
                .body("id", hasItem(1))
                .body("number",hasItem("148"));
    }



}
