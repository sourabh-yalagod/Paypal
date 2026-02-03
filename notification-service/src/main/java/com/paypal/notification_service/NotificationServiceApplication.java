package com.paypal.notification_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication implements CommandLineRunner {
    private int port;
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
    @Override
    public void run(String... args) throws Exception {
        System.out.println("http://localhost:" + port);
    }
}
