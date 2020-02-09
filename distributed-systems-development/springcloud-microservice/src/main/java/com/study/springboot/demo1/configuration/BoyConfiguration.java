package com.study.springboot.demo1.configuration;

import com.study.springboot.demo1.domain.Boy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class BoyConfiguration {

    @Bean
    public Boy boyBean(){

        return new Boy("kane",32);
    }
}
