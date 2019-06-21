package com.mnan2c.fms;

import com.mnan2c.fms.repository.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
    basePackages = {"com.mnan2c.fms.repository"},
    repositoryBaseClass = BaseRepositoryImpl.class)
@SpringBootApplication
public class FmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(FmsApplication.class, args);
  }
}
