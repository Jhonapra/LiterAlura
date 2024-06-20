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
    private List<String> lenguaje;
    private String MODELO_IMPRESION_LIBROS ="""
                ******************************
                %s
                ------------------------------ 
                Autor: %s
                Idioma: %s
                Cantidad de descargas: %s
                ------------------------------
                ******************************
                """;

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
                    muestraElMenuIdiomas();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    public void muestraElMenuIdiomas() throws JsonProcessingException {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ***Ingrese el numero del idioma que quiere los libros***
                    
                    1. Español
                    2. Frances
                    3. Ingles
                    4. Portugues
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    lenguaje = new ArrayList<>(Arrays.asList("es"));
                    buscarLibrosPorIdioma();
                    break;
                case 2:
                    lenguaje = new ArrayList<>(Arrays.asList("fr"));
                    buscarLibrosPorIdioma();
                    break;

                case 3:
                    lenguaje = new ArrayList<>(Arrays.asList("en"));
                    buscarLibrosPorIdioma();
                    break;

                case 4:
                    lenguaje = new ArrayList<>(Arrays.asList("pt"));
                    buscarLibrosPorIdioma();
                    break;


                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }


    private List<String> pruebaLibrosAutor(String nombreDelAutor){
        List<Libro> prueba = repository.librosAutor(nombreDelAutor);
        List<String> librosAutor = new ArrayList<>();
        prueba.stream().forEach(e->librosAutor.add(e.getNombre()));
        System.out.println(librosAutor);
        return librosAutor;

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
        Libro libroAGuardar = new Libro(datosLibro);
        Autor autor = new Autor(datosAutor, libroAGuardar);
        libroAGuardar.setAutor(autor);
        repository.save(libroAGuardar);
        System.out.printf(MODELO_IMPRESION_LIBROS, datosLibro.nombre(),
                datosAutor.nombre(), datosLibro.lenguaje(), datosLibro.descargas());
    }

    private void librosRegistrados() {
        libros = repository.findAll();
        libros.stream().sorted(Comparator.comparing(Libro::getIdLibro)).
                forEach(e-> System.out.printf(MODELO_IMPRESION_LIBROS, e.getNombre(), e.getAutor().getNombre(),
                        e.getLenguaje(), e.getDescargas()));
    }

    private void autoresRegistrados() {
        tomarDatosAutores().forEach(e-> System.out.printf("""
                *****************************
                Autor:%s
                Fecha de nacimiento:%d
                Fecha de fallecimiento:%d
                Libros: %s
                *****************************
                """, e.getNombre(), e.getAnoDeNacimiento(), e.getAnoDeMuerte(), pruebaLibrosAutor(e.getNombre())));
    }

    private void autoresVivosEnDeterminadoAno() {
        System.out.println("Escribe el año en el que crees que estaba vivo el autor: ");
        var anoProbableDeVidaAutor = scanner.nextInt();

        List<Autor>autoresVivos = repository.autoresVivos(anoProbableDeVidaAutor);
        autoresVivos.stream().forEach(e-> System.out.printf("""
                *****************************
                Autor:%s
                Fecha de nacimiento:%d
                Fecha de fallecimiento:%d
                Libros: %s
                *****************************
                """, e.getNombre(), e.getAnoDeNacimiento(), e.getAnoDeMuerte(), pruebaLibrosAutor(e.getNombre())));

    }

    private void buscarLibrosPorIdioma() throws JsonProcessingException {
        List<Libro>librosFlijtradosPorIdioma = repository.librosPorLenguaje(lenguaje);
        librosFlijtradosPorIdioma.stream().forEach(e-> System.out.printf(MODELO_IMPRESION_LIBROS, e.getNombre(),
                e.getAutor().getNombre(), e.getLenguaje(), e.getDescargas()));

    }


}
