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
                ******************************\n
                """;
    private String ERROR_NUMERO_INVALIDO= """
                        **********************ERROR***************************
                               ||Recuerda ingresar un numero valido||
                        
                        -Los numeros validos se encuentran en la tabla.
                        ******************************************************\n
                        """;

    public CodigoPrincipal(LibroRepository repository){
        this.repository = repository;
    }

    public void muestraElMenu() throws JsonProcessingException {
        var opcionMenu = -1;
        while (opcionMenu != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Mostrar lista de libros registrados
                    3 - Mostrar lista de autores registrados
                    4 - Mostrar lista de autores vivos en determinado año
                    5 - Mostrar lista de libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);

            try{
                opcionMenu = scanner.nextInt();
                scanner.nextLine();

            }catch (Exception e){
                System.out.println(ERROR_NUMERO_INVALIDO);
                scanner = new Scanner(System.in);
            }



            switch (opcionMenu) {
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
                    System.out.println("||Opción inválida||");
            }
            if (opcionMenu != 0){
                opcionMenu=-1;
            }else{
                opcionMenu=0;
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

            try{
                opcion = scanner.nextInt();
                scanner.nextLine();

            }catch (Exception e){
                System.out.println(ERROR_NUMERO_INVALIDO);
                scanner = new Scanner(System.in);
            }

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
                    System.out.println("||Opción inválida||");
            }
            if (opcion != 0){
                opcion=-1;
            }else{
                opcion=0;
            }
        }

    }


    private List<String> pruebaLibrosAutor(String nombreDelAutor){
        List<Libro> prueba = repository.librosAutor(nombreDelAutor);
        List<String> librosAutor = new ArrayList<>();
        prueba.stream().forEach(e->librosAutor.add(e.getNombre()));
        return librosAutor;

    }

    //Imprime la ficha con los datos relevantes del autor(Requiere una lista del tipo Autor para funcionar)
    private void imprimirModeloAutores(List<Autor> autorList){
        autorList.stream().forEach(e-> System.out.printf("""
                *****************************
                Autor:%s
                Fecha de nacimiento:%d
                Fecha de fallecimiento:%d
                Libros: %s
                *****************************\n
                """, e.getNombre(), e.getAnoDeNacimiento(), e.getAnoDeMuerte(), pruebaLibrosAutor(e.getNombre())));
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
        libros = repository.findAll();
        Optional<Libro>libro= libros.stream()
                .filter(s->s.getNombre().toLowerCase().contains(libroABuscar.toLowerCase()))
                .findFirst();
        if (resultadosLibro.cantidadResultados()==0){
            System.out.println("""
                    ***********************************
                    No se encontraron resultados
                    ***********************************\n""");

        }else if (libro.isPresent()){
            System.out.println("""
                    ***************************************
                    El libro ya esta en la base de datos
                    ***************************************\n""");

        }else {
            DatosLibro datosLibro = resultadosLibro.datosLibro().get(0);
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Libro libroAGuardar = new Libro(datosLibro);
            Autor autor = new Autor(datosAutor, libroAGuardar);
            libroAGuardar.setAutor(autor);
            repository.save(libroAGuardar);
            System.out.printf(MODELO_IMPRESION_LIBROS, datosLibro.nombre(),
                    datosAutor.nombre(), datosLibro.lenguaje(), datosLibro.descargas());

        }

    }

    private void librosRegistrados() {
        libros = repository.findAll();
        libros.stream().sorted(Comparator.comparing(Libro::getIdLibro)).
                forEach(e-> System.out.printf(MODELO_IMPRESION_LIBROS, e.getNombre(), e.getAutor().getNombre(),
                        e.getLenguaje(), e.getDescargas()));
    }

    private void autoresRegistrados() {
        imprimirModeloAutores(tomarDatosAutores());
    }

    private void autoresVivosEnDeterminadoAno() {
        System.out.println("Escribe el año en el que crees que estaba vivo el autor: ");
        var anoProbableDeVidaAutor = scanner.nextInt();

        List<Autor>autoresVivos = repository.autoresVivos(anoProbableDeVidaAutor);
        imprimirModeloAutores(autoresVivos);

    }

    private void buscarLibrosPorIdioma() throws JsonProcessingException {
        List<Libro>librosFlijtradosPorIdioma = repository.librosPorLenguaje(lenguaje);
        librosFlijtradosPorIdioma.stream().forEach(e-> System.out.printf(MODELO_IMPRESION_LIBROS, e.getNombre(),
                e.getAutor().getNombre(), e.getLenguaje(), e.getDescargas()));

    }


}
