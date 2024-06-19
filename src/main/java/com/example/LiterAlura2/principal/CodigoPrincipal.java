package com.example.LiterAlura2.principal;

import com.example.LiterAlura2.modelos.*;
import com.example.LiterAlura2.repositori.LibroRepository;
import com.example.LiterAlura2.service.ObtenerJsonApi;
import com.example.LiterAlura2.service.TransformarDatosAClase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.*;

public class CodigoPrincipal {
    private TransformarDatosAClase transformarDatosAClase = new TransformarDatosAClase();
    private ObtenerJsonApi obtenerJsonApi = new ObtenerJsonApi();
    private Scanner scanner = new Scanner(System.in);
    private String URL_BASE = "https://gutendex.com/books/";
    private LibroRepository repository;
    private List<Libro> libros;

    public CodigoPrincipal(LibroRepository repository){
        this.repository = repository;
    }

    public void muestraElMenu() throws JsonProcessingException {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Mostrar lista de libros registrados
                    3 - Mostrar lista de autores registrados
                    4 - Mostrar lista de autores vivos en determinado año
                    5 - Mostrar lista de libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    librosRegistrados();
                    break;

                case 3:
                    autoresRegistrados();
                    break;

                case 4:
                    autoresVivosEnDeterminadoAno();
                    break;

                case 5:
                    librosPorIdioma();
                    break;

                

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }


    private List<Autor> tomarDatosAutores(){
        List<Autor> datosAutoresRegistrados = repository.autoresRegistradosBaseDeDatos();
        return datosAutoresRegistrados;
    }

    private void buscarLibroPorTitulo() throws JsonMappingException, JsonProcessingException {
        System.out.println("Escriba el nombre del libro que desea buscar");
        var libroABuscar = scanner.nextLine();
        var json = obtenerJsonApi.obtenerDatos(URL_BASE + "?search="+libroABuscar.
                replace(" ","%20"));
        ResultadosLibro resultadosLibro = transformarDatosAClase.obtenerDatos(json, ResultadosLibro.class);
        DatosLibro datosLibro = resultadosLibro.datosLibro().get(0);
        DatosAutor datosAutor = datosLibro.autor().get(0);
        System.out.println(datosAutor);
        Libro libroAGuardar = new Libro(datosLibro);
        Autor autor = new Autor(datosAutor, libroAGuardar);
        libroAGuardar.setAutor(autor);
        repository.save(libroAGuardar);
    }

    private void librosRegistrados() {
        libros = repository.findAll();
        libros.stream().sorted(Comparator.comparing(Libro::getIdLibro)).
                forEach(System.out::println);
    }

    private void autoresRegistrados() {
        tomarDatosAutores().forEach(e-> System.out.println("Nombre Autor: " + e.getNombre()+"\n"));
    }

    private void autoresVivosEnDeterminadoAno() {
        System.out.println("Escribe el año en el que crees que estaba vivo el autor: ");
        var anoProbableDeVidaAutor = scanner.nextInt();

        tomarDatosAutores().forEach(e-> {
            if (anoProbableDeVidaAutor >= e.getAnoDeNacimiento() && e.getAnoDeMuerte() > anoProbableDeVidaAutor ) {
                System.out.println("El compa estaba vivo: "+ e.getNombre());
            }else {
                System.out.println("En ese entonces no estaba vivo");
            }
        });

    }

    private void librosPorIdioma() {

    }


}
