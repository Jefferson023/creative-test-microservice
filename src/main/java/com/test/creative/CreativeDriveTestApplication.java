package com.test.creative;

import com.test.creative.util.TokenUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CreativeDriveTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreativeDriveTestApplication.class, args);
	}

	@Bean
	public TokenUtil tokenUtilBean() {
		return new TokenUtil();
	}
}
