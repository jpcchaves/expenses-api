package com.expenses.app.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfigurerConfig implements AsyncConfigurer {

  @Bean
  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();

    threadPoolExecutor.setCorePoolSize(10);
    threadPoolExecutor.setMaxPoolSize(20);
    threadPoolExecutor.setQueueCapacity(500);
    threadPoolExecutor.setThreadNamePrefix("Async Thread");

    threadPoolExecutor.initialize();

    return threadPoolExecutor;
  }
}
