package com.ices4hu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@SpringBootApplication
@EnableScheduling
public class DemoApplication {
//
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
