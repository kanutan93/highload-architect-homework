package ru.hl.primaryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PrimaryServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(PrimaryServiceApplication.class, args);
  }
}
