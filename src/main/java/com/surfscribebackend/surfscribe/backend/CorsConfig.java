package com.surfscribebackend.surfscribe.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://main.d2o4jnykp1k7oq.amplifyapp.com/", "http://main.d2o4jnykp1k7oq.amplifyapp.com/", "http://www.surfscribe.org", "https://www.surfscribe.org", "http://surfscribe.org", "https://surfscribe.org") // Replace with your frontend's URL
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
    }
}
