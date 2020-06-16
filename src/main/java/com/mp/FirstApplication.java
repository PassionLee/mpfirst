package com.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.mp.dao")
@SpringBootApplication
public class FirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstApplication.class, args);
    }

}
