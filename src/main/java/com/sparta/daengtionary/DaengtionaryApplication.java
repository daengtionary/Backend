package com.sparta.daengtionary;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableJpaAuditing
public class DaengtionaryApplication {
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
        + "classpath:application.properties,"
        + "classpath:application-alpha.yml,"
        + "classpath:application-local.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(DaengtionaryApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}