package com.example.LiterAlura2;

import com.example.LiterAlura2.principal.CodigoPrincipal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAlura2Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiterAlura2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CodigoPrincipal principal = new CodigoPrincipal();
		principal.muestraElMenu();
	}
}
