package com.akashyalameli.authbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuthbridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthbridgeApplication.class, args);
	}

}
