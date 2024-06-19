package com.example.LiterAlura2.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

@Entity
@Table
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String nombre;
    int anoDeNacimiento;
    int anoDeMuerte;
    @OneToOne
    @JoinColumn(name = "id_libro")
    private Libro libro;

    //Un constructor predeterminado para que lo pueda ejecutar spring correctamente
    public Autor(){}

    public Autor(DatosAutor datosAutor, Libro libro) {
        this.nombre = datosAutor.nombre();
        this.anoDeNacimiento = datosAutor.anoDeNacimiento();
        this.anoDeMuerte = datosAutor.anoDeMuerte();
        this.libro = libro;

    }

    @Override
    public String toString() {
        return "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", anoDeNacimiento=" + anoDeNacimiento +
                ", anoDeMuerte=" + anoDeMuerte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnoDeNacimiento() {
        return anoDeNacimiento;
    }

    public void setAnoDeNacimiento(int anoDeNacimiento) {
        this.anoDeNacimiento = anoDeNacimiento;
    }

    public int getAnoDeMuerte() {
        return anoDeMuerte;
    }

    public void setAnoDeMuerte(int anoDeMuerte) {
        this.anoDeMuerte = anoDeMuerte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
