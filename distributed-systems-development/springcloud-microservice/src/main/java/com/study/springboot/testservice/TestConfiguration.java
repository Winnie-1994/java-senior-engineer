package com.study.springboot.testservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
public class TestConfiguration {

    @Bean
    @Profile("dev")
    public void printOut(){
        System.out.println("我现在已经被加载--在启动类以外");
    }
}
