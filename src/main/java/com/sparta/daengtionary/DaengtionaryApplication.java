package com.sparta.daengtionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class DaengtionaryApplication {
	public static void main(String[] args) {
		SpringApplication.run(DaengtionaryApplication.class, args);
	}
}