package com.sparta.daengtionary.aop.server;

import org.joda.time.LocalTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
public class controller {
    private final LocalTime now = new LocalTime();

    @GetMapping("/")
    public String Home() {
        return "HomeTest " + LocalDate.now() +"동작 확인"+now ;
    }
}
