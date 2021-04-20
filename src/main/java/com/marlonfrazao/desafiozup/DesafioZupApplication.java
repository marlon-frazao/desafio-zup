package com.marlonfrazao.desafiozup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DesafioZupApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioZupApplication.class, args);
	}

}
