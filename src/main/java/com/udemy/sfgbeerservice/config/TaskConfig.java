package com.udemy.sfgbeerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@Configuration
public class TaskConfig {

    @Bean
    TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }

}

/**
 * Spring Scheduling Annotations
 * Ca permet d'executer des taches en asynchro
 * When single-threaded execution isn't enough, we can use annotations from the org.springframework.scheduling.annotation package.
 *
 * @EnableAsync: we can enable asynchronous functionality. We must use it with @Configuration:
 * @EnableScheduling: we can enable scheduling in the application. We also have to use it in conjunction with @Configuration:
 */