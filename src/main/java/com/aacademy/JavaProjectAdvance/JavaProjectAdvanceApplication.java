package com.aacademy.JavaProjectAdvance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class JavaProjectAdvanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaProjectAdvanceApplication.class, args);
	}

}
