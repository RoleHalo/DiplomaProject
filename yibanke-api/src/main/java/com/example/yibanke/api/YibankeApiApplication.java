package com.example.yibanke.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@ServletComponentScan
@Slf4j
@EnableAsync
@SpringBootApplication
public class YibankeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YibankeApiApplication.class, args);
    }

}
