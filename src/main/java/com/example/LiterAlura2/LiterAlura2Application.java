package com.example.LiterAlura2;

import com.example.LiterAlura2.principal.CodigoPrincipal;
import com.example.LiterAlura2.repositori.LibroRepository;
import com.example.LiterAlura2.service.ITransformarDatosAClase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAlura2Application implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAlura2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CodigoPrincipal principal = new CodigoPrincipal(repository);
		principal.muestraElMenu();
	}

}
