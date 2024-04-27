package com.cashrich.CashRichAssessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CashRichAssessmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CashRichAssessmentApplication.class, args);
    }

    @Bean

    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
