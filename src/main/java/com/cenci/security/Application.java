package com.cenci.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Profile(value = "development")
	@Bean
    CommandLineRunner init(BulkAdminUser bulkAdminUser) {
        return args -> bulkAdminUser.initRootAdmin();
    }
}
