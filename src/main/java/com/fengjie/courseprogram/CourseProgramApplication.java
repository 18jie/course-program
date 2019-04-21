package com.fengjie.courseprogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.fengjie.courseprogram.mybatis.mappers")
public class CourseProgramApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseProgramApplication.class, args);
    }

}
