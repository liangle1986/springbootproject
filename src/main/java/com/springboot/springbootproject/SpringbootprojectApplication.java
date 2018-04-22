package com.springboot.springbootproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringbootprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootprojectApplication.class, args);
	}
}
