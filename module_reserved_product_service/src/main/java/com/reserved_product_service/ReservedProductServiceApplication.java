package com.reserved_product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com")
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients
public class ReservedProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservedProductServiceApplication.class, args);
	}

}
