package com.aacademy.JavaProjectAdvance.controller.integration;


import com.aacademy.JavaProjectAdvance.converter.BusConverter;
import com.aacademy.JavaProjectAdvance.dto.BusDto;
import com.aacademy.JavaProjectAdvance.dto.DriverDto;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.model.Driver;
import com.aacademy.JavaProjectAdvance.repository.DriverRepository;
import com.aacademy.JavaProjectAdvance.service.BusService;
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
public class DriverControllerIntTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private BusService busService;

    @Autowired
    private BusConverter busConverter;

    @Test
    public void saveDriver() throws JsonProcessingException {
        Bus bus = busService.save(Bus.builder().id(5L).number("21a").build());
        BusDto busDto = busConverter.toBusDto(bus);
        DriverDto driverDto = DriverDto.builder().driverNumber("12345").busId(busDto.getId()).build();
        String reqJson = objectMapper.writeValueAsString(driverDto);

        given()
                .contentType(ContentType.JSON.toString())
                .body(reqJson)
                .when()
                .post("http://localhost:8080/drivers")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("driverNumber", equalTo("12345"));
    }

    @Test
    public void updateDriver() throws JsonProcessingException {
        Bus bus = busService.save(Bus.builder().id(5L).number("21a").build());
        BusDto busDto = busConverter.toBusDto(bus);

        driverRepository.save(Driver.builder().id(1L).bus(bus).driverNumber("12345").build());
        DriverDto driverDto = DriverDto.builder().id(1L).busId(busDto.getId()).driverNumber("12365").build();

        String reqJson = objectMapper.writeValueAsString(driverDto);

        given()
                .contentType(ContentType.JSON.toString())
                .body(reqJson)
                .when()
                .put("http://localhost:8080/drivers/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("driverNumber", equalTo("12365"));
    }

    @Test
    public void  deleteDriver() throws JsonProcessingException {
        Bus bus = busService.save(Bus.builder().id(5L).number("21a").build());

        Driver driver = driverRepository.save(Driver.builder().id(1L).bus(bus).driverNumber("12345").build());
        String reqJson = objectMapper.writeValueAsString(DriverDto.builder()
                .id(driver.getId())
                .driverNumber(driver.getDriverNumber())
                .busId(driver.getBus().getId()).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .delete("http://localhost:8080/drivers/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void findDriverById() throws JsonProcessingException {
        Bus bus = busService.save(Bus.builder().id(5L).number("21a").build());

        Driver driver = driverRepository.save(Driver.builder().id(1L).bus(bus).driverNumber("12345").build());
        String reqJson = objectMapper.writeValueAsString(DriverDto.builder()
                .id(driver.getId())
                .driverNumber(driver.getDriverNumber())
                .busId(driver.getBus().getId()).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .get("http://localhost:8080/drivers/id/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("driverNumber", equalTo("12345"));
    }

    @Test
    public void findDriverByDriverNumber() throws JsonProcessingException {
        Bus bus = busService.save(Bus.builder().id(5L).number("21a").build());

        Driver driver = driverRepository.save(Driver.builder().id(1L).bus(bus).driverNumber("12345").build());
        String reqJson = objectMapper.writeValueAsString(DriverDto.builder()
                .id(driver.getId())
                .driverNumber(driver.getDriverNumber())
                .busId(driver.getBus().getId()).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .get("http://localhost:8080/drivers/number/12345")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("driverNumber", equalTo("12345"));
    }

    @Test
    public void findAllDrivers() throws JsonProcessingException {
        Bus bus = busService.save(Bus.builder().id(5L).number("21a").build());

        Driver driver = driverRepository.save(Driver.builder().id(1L).bus(bus).driverNumber("12345").build());
        String reqJson = objectMapper.writeValueAsString(DriverDto.builder()
                .id(driver.getId())
                .driverNumber(driver.getDriverNumber())
                .busId(driver.getBus().getId()).build());

        given()
                .contentType(ContentType.JSON)
                .body(reqJson)
                .when()
                .get("http://localhost:8080/drivers")
                .then()
                .statusCode(200)
                .body("id", hasItem(1))
                .body("driverNumber", hasItem("12345"));
    }
}
