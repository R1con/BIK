package com.telegram.bot.bik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan("com.telegram.bot.bik.config.properties")
public class BikApplication {
	public static void main(String[] args) {
		SpringApplication.run(BikApplication.class, args);
	}

}
