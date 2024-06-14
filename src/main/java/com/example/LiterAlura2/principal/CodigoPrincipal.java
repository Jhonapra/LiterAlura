package com.example.LiterAlura2.principal;

import com.example.LiterAlura2.modelos.DatosLibro;
import com.example.LiterAlura2.modelos.ResultadosLibro;
import com.example.LiterAlura2.service.ObtenerJsonApi;
import com.example.LiterAlura2.service.TransformarDatosAClase;

import java.util.Scanner;

public class CodigoPrincipal {
    private TransformarDatosAClase transformarDatosAClase = new TransformarDatosAClase();
    private ObtenerJsonApi obtenerJsonApi = new ObtenerJsonApi();
    private Scanner scanner = new Scanner(System.in);
    private String URL_BASE = "https://gutendex.com/books/";


    public void muestraElMenu() {
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

    private ResultadosLibro getDatosLibro(){
        System.out.println("Escriba el nombre del libro que desea buscar");
        var libroABuscar = scanner.nextLine();
        var json = obtenerJsonApi.obtenerDatos(URL_BASE + "?search="+libroABuscar.replace(" ","%20"));
        var impresion = transformarDatosAClase.obtenerDatos(json, ResultadosLibro.class);
        System.out.println(impresion);
        return transformarDatosAClase.obtenerDatos(json, ResultadosLibro.class);

    }

    private void buscarLibroPorTitulo() {
        getDatosLibro();
    }

    private void librosRegistrados() {
    }

    private void autoresRegistrados() {

    }

    private void autoresVivosEnDeterminadoAno() {

    }

    private void librosPorIdioma() {

    }


}
