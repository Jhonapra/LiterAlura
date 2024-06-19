package com.example.LiterAlura2.modelos;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int idLibro;
    private String nombre;
    @OneToOne(mappedBy = "libro", cascade = CascadeType.ALL)
    private Autor autor;
    private List<String> lenguaje;

    //Un constructor predeterminado para que lo pueda ejecutar spring correctamente
    public Libro(){}

    public Libro(DatosLibro libro) {
        this.idLibro = libro.idLibro();
        this.nombre = libro.nombre();
        this.lenguaje = libro.lenguaje();
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", idLibro=" + idLibro +
                ", nombre='" + nombre + '\'' +
                ", autor=" + autor +
                ", lenguaje=" + lenguaje +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(List<String> lenguaje) {
        this.lenguaje = lenguaje;
    }
}
