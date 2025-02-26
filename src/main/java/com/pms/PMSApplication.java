package com.pms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PMSApplication extends SpringBootServletInitializer {
    
    @Value("${max-login-attempts}")
    private int maxLoginAttempts;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PMSApplication.class);
    }
    
    
    public static void main(String[] args) {
        SpringApplication.run(PMSApplication.class, args);
        System.out.println("Working!!");
    }

   
}
