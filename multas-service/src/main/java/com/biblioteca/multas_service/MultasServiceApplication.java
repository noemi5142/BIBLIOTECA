package com.biblioteca.multas_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // CLASE 12
public class MultasServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultasServiceApplication.class, args);
    }
}