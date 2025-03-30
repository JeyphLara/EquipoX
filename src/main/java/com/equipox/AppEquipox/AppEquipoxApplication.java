package com.equipox.AppEquipox;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

import com.equipox.AppEquipox.Sales.models.PaymentMethodModel;
import com.equipox.AppEquipox.Sales.repositories.PaymentMethodRepository;
import java.util.List;

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

	/* Esto es un seeder para 'plantar' la info  */
	@Bean
    CommandLineRunner seedPaymentMethods(PaymentMethodRepository repository) {
        return args -> {
            List<String> methods = List.of("Efectivo", "Tarjeta", "Transferencia");
            for (String name : methods) {
                if (repository.findByMethodName(name) == null) {
                    repository.save(new PaymentMethodModel(null, name));
                }
            }
        };
    }

}
