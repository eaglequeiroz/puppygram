package com.rti.puppygram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PuppygramApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuppygramApplication.class, args);
	}

}
