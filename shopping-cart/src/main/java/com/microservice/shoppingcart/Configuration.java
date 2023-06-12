package com.microservice.shoppingcart;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "com.microservice.shoppingcart.*")
public class Configuration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET,POST,PUT,DELETE").allowedOrigins("http://localhost:4200");
    }
}
