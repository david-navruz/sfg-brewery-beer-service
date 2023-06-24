package com.udemy.sfgbeerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(exclude = ArtemisAutoConfiguration.class)
public class SfgBeerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SfgBeerServiceApplication.class, args);
	}

}
