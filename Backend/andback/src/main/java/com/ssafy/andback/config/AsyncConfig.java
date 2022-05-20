package com.ssafy.andback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
*
* AsyncConfig
* 비동기처리를 위한 설정 값
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-19
* 마지막 수정일 2022-04-19
**/

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {


    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2); // 기본적으로 실행 대기 중인 Thread 개수
        executor.setMaxPoolSize(10); //동시에 작동하는 최대 Thread 개수
        executor.setQueueCapacity(500); // CorePool이 초과될 떄 Queue에 저장했다가 꺼내서 실행한다.
        // 단, MaxPoolSize 가 초과되면 Thread 생성에 실패할 수 있음.
        executor.setThreadNamePrefix("async-"); // Spring 에서 생성하는 Thread 이름의 접두사
        executor.initialize();
        return executor;
    }

}
