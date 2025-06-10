package com.momentree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MomentreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomentreeApplication.class, args);
	}

}
