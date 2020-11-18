package com.example.demo.homework01;

import org.springframework.stereotype.Component;

/**
 * 注解
 * @Author: th
 */

@Component
public class AutoExample {
    public AutoExample(){
        System.out.println("构造函数");
    }
    public void info(){
        System.out.println("自动装配");
    }
}
