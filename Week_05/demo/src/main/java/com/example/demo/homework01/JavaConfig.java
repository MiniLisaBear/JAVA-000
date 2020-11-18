package com.example.demo.homework01;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
    public JavaExample javaExample(){
        return new JavaExample();
    }
}
