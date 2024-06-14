package com.example.LiterAlura2.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadosLibro(@JsonAlias("results") List<DatosLibro> datosLibro) {
}
