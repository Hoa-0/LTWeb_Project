package com.alotra.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AlotraWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlotraWebApplication.class, args);
	}

}
