package by.spaces.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

import static by.spaces.calculator.calculations.ExtractLibClass.extractLibrary;

@SpringBootApplication
public class Main {
    public static void main(String[] args)  {
        try {
            System.load(extractLibrary("Converter"));
            System.load(extractLibrary("Matrix"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(    CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*");
            }
        };
    }
}
