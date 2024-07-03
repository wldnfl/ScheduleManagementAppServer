package com.sparta.schedulemanagementappserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest
@EnableJpaAuditing
class ScheduleManagementAppServerApplicationTests {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleManagementAppServerApplication.class, args);
    }
}
