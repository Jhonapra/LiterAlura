package com.example.LiterAlura2.repositori;

import com.example.LiterAlura2.modelos.Autor;
import com.example.LiterAlura2.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT a FROM Libro l JOIN l.autor a")
    List<Autor>autoresRegistradosBaseDeDatos();



}
