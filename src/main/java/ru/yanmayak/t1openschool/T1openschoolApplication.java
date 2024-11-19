package ru.yanmayak.t1openschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class T1openschoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(T1openschoolApplication.class, args);
    }

}
