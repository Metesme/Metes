package com.eternal.config;

import cn.hutool.core.thread.ExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jiajunmei
 */
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ExecutorService getThreadPool(){
        return ExecutorBuilder.create()
                .setCorePoolSize(15)
                .setMaxPoolSize(30)
                .setWorkQueue(new LinkedBlockingQueue<>(100))
                .build();
    }
}
