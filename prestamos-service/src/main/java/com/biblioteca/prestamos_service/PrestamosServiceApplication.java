package com.biblioteca.prestamos_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // CLASE 12

@SpringBootApplication
@EnableFeignClients // <--- OBLIGATORIO PARA QUE FUNCIONE FEIGN
public class PrestamosServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrestamosServiceApplication.class, args);
    }
}
