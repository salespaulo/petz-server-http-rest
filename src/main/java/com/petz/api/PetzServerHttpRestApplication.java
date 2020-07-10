package com.petz.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@EnableJpaRepositories
@SpringBootApplication(scanBasePackageClasses = PetzServerHttpRestApplication.class)
public class PetzServerHttpRestApplication {

	public static void main(String[] args) {
		healthy(SpringApplication.run(PetzServerHttpRestApplication.class, args));
	}
	
	private static void healthy(ConfigurableApplicationContext context) {
		log.info("Running Context Id={} !", context.getId());
	}

}
