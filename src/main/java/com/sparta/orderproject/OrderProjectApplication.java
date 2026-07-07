package com.sparta.orderproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OrderProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderProjectApplication.class, args);
    }

}
