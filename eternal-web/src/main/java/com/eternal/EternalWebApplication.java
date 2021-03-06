package com.eternal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.eternal.mapper")
@ComponentScan(value = "com.eternal", excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {
        }
))
@EnableAsync
public class EternalWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EternalWebApplication.class, args);
    }

}

