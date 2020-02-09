package com.study.springboot.demo1.controller;

import com.study.springboot.demo1.configuration.BoyConfiguration;
import com.study.springboot.demo1.properties.MyPropertiesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    BoyConfiguration boyConfiguration;


    @Autowired
    MyPropertiesBean myPropertiesBean;

    @GetMapping("/get-string")
    public String getString(){
        return boyConfiguration.boyBean().getName();
//    return myPropertiesBean.getName();
    }
}
