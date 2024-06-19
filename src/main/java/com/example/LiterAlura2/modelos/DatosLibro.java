package com.example.LiterAlura2.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(@JsonAlias("id") int idLibro,
                         @JsonAlias("title") String nombre,
                         @JsonAlias("authors") List <DatosAutor> autor,
                         @JsonAlias("languages") List<String> lenguaje) {


}
