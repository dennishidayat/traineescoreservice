package com.enigma.task.traineescore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.enigma.task.traineescore.config.DaoSpringConfig;

@ComponentScan(basePackages={"com.enigma.task.traineescore"})
@EnableJpaRepositories({"com.enigma.task.traineescore.repository"})
@EntityScan({"com.enigma.task.traineescore.model", "com.enigma.task.bootcampmaterial.model", "com.enigma.task.trainee.model"})
@Import({DaoSpringConfig.class})
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
