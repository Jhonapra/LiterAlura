package com.example.LiterAlura2.repositori;

import com.example.LiterAlura2.modelos.Autor;
import com.example.LiterAlura2.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT a FROM Libro l JOIN l.autor a")
    List<Autor>autoresRegistradosBaseDeDatos();

    @Query("SELECT l FROM Libro l WHERE l.lenguaje IN :lenguaje")
    List<Libro>librosPorLenguaje(List<String> lenguaje);

    @Query("SELECT v FROM Libro l JOIN l.autor v WHERE v.anoDeNacimiento <= :anoProbableDeVida AND v.anoDeMuerte > :anoProbableDeVida")
    List<Autor>autoresVivos(int anoProbableDeVida);

    @Query("SELECT l FROM Libro l WHERE EXISTS(SELECT a FROM l.autor a WHERE a.nombre = :nombreAutor)")
    List<Libro>librosAutor(String nombreAutor);





}
