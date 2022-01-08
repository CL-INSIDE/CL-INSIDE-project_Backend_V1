package com.example.clinside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClinsideApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinsideApplication.class, args);
    }

}
