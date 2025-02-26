package com.pms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private JwtConfig jwtConfig; // Inject JwtConfig

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // Print CORS properties
        //System.out.println("Allowed Origins: " + String.join(", ", corsConfig.getAllowedOrigins()));
       // System.out.println("Upload Path: " + corsConfig.getUploadPath());

        // Print JWT properties
       // System.out.println("JWT Secret: " + jwtConfig.getSecret());
       // System.out.println("JWT Expiration Time (in milliseconds): " + jwtConfig.getExpirationTime());

        // Configure CORS mapping
        registry.addMapping("/**")
                .allowedOrigins(corsConfig.getAllowedOrigins())
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
