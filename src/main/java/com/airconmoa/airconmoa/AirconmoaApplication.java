package com.airconmoa.airconmoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.airconmoa.airconmoa")
@EnableJpaRepositories("com.airconmoa.airconmoa")
public class AirconmoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirconmoaApplication.class, args);
	}

}
