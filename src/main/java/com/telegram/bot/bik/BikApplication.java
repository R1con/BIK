package com.telegram.bot.bik;

import com.telegram.bot.bik.service.scheduler.SchedulerInvoker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan("com.telegram.bot.bik.config.properties")
public class BikApplication {
	public static void main(String[] args) {
		SpringApplication.run(BikApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			SchedulerInvoker invoker = ctx.getBean(SchedulerInvoker.class);
			invoker.invoke();
		};
	}


}
