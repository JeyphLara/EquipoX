package com.equipox.AppEquipox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class AppEquipoxApplication {

	@GetMapping("/")
	public String home() {
		return "Welcome to the Equipox App";
	}

	@GetMapping("/api/v1")
	public String home2() {
		return "Welcome to the Equipox App";
	}

	@GetMapping("/error")
	public String error() {
		return "Welcome to the Equipox App";
	}

	public static void main(String[] args) {
		SpringApplication.run(AppEquipoxApplication.class, args);
	}

}
