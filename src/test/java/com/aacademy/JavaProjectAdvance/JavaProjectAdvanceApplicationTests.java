package com.aacademy.JavaProjectAdvance;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
@EntityScan("com.aacademy.JavaProjectAdvance.model")
@EnableJpaRepositories("com.aacademy.JavaProjectAdvance.repository")
@ComponentScan(basePackages = {"com.aacademy.JavaProjectAdvance.service.BusService", "com.aacademy.JavaProjectAdvance.service.StationService", "com.aacademy.JavaProjectAdvance.service.DriverService"})
*/

@SpringBootTest
class JavaProjectAdvanceApplicationTests {

	@Test
	void contextLoads() {
	}

}
