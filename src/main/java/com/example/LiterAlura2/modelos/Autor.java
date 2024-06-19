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
    @OneToOne(mappedBy = "Autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Libro libro;

    //Un constructor predeterminado para que lo pueda ejecutar spring correctamente
    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = nombre;
        this.anoDeNacimiento = anoDeNacimiento;
        this.anoDeMuerte = anoDeMuerte;
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
}
