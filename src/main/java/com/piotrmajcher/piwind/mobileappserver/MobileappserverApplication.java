package com.piotrmajcher.piwind.mobileappserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MobileappserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileappserverApplication.class, args);
	}
}
