package com.summit.gym.Sumit_Gym_Management_System.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")  // Apply to all endpoints
////                .allowedOrigins("http://localhost:*","file://*")  // Allow localhost with any port
////                .allowedOrigins("*")  // Allow localhost with any port
//                .allowedOrigins("null")  // Allow localhost with any port
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow these methods
//                .allowedHeaders("*")  // Allow all headers
////                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//                .exposedHeaders("*")
////                .allowCredentials(true)  // Allow credentials (cookies, sessions)
//                .maxAge(3600);  // Cache preflight responses for 1 hour
//    }
//}


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Apply to all endpoints
                .allowedOrigins("null", "http://localhost:*", "http://localhost:63342")  // Allow localhost with any port
//                .allowedOrigins("*")  // Allow localhost with any port
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow these methods
                .allowedHeaders("*")  // Allow all headers
                .exposedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);  // Cache preflight responses for 1 hour
    }
}
