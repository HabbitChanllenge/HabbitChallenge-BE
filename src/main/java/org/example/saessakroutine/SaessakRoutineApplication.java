package org.example.saessakroutine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling //@Scheduled를 사용하기 위해 필요하다
public class SaessakRoutineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaessakRoutineApplication.class, args);
    }

}
