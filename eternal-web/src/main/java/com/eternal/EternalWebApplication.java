package com.eternal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eternal.mapper")
public class EternalWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EternalWebApplication.class, args);
    }

}
