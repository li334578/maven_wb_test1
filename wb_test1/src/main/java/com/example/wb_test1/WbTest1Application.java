package com.example.wb_test1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class WbTest1Application {

    public static void main(String[] args) {
        SpringApplication.run(WbTest1Application.class, args);
    }

}
