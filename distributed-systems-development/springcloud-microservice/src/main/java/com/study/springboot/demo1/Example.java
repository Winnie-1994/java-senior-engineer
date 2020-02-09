package com.study.springboot.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication(scanBasePackages = {"com.study.springboot.**"})
public class Example {



    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Example.class);
        springApplication.setAdditionalProfiles();
        Properties properties = new Properties();
        properties.setProperty("names","study");
        springApplication.setDefaultProperties(properties);
        springApplication.run(args);
    }
}
