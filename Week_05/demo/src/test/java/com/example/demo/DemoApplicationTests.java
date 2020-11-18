package com.example.demo;

import com.example.demo.homework01.AutoExample;
import com.example.demo.homework01.JavaConfig;
import com.example.demo.homework01.XmlExample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private AutoExample autoExample;
    @Autowired
    private JavaConfig javaConfig;
//    @Autowired
//    MySchool mySchool;

    @Test
    void contextLoads() {
    }

    @Test
    public void testAuto(){
        autoExample.info();
    }
    @Test
    public void testJava(){
        javaConfig.javaExample().info();
    }
    @Test
    public void testXml(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("BeanConfig.xml");
        XmlExample xmlExample = (XmlExample) context.getBean("XmlExample");
        xmlExample.info();
    }
//    @Test
//    public void test() {
//        System.out.println(mySchool.toString());
//    }
}
