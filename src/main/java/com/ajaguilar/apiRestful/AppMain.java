package com.ajaguilar.apiRestful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
//@ComponentScan(basePackageClasses = AppMain.class)
//@EntityScan("com.ajaguilar.apiRestful.model")
public class AppMain {

	public static void main(String[] args) {
		SpringApplication.run(AppMain.class, args);

	}

}
