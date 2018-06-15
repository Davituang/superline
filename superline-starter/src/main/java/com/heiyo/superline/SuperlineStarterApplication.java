package com.heiyo.superline;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class SuperlineStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperlineStarterApplication.class, args);
	}
}
