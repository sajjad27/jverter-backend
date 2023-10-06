package com.jverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
public class JverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(JverterApplication.class, args);
	}

}
