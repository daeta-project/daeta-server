package com.example.daetaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class DaetaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaetaServerApplication.class, args);
    }

}
