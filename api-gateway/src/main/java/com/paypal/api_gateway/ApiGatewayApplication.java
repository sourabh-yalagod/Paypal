package com.paypal.api_gateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
    @Override
    public void run(String... args) throws Exception{
        System.out.println("http:localhost:8080");
    }
}
