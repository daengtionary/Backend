package com.sparta.daengtionary.aop.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class controller {

    @GetMapping("/")
    public String Home(){
        return "HomeTest " + LocalDate.now();
    }
}
