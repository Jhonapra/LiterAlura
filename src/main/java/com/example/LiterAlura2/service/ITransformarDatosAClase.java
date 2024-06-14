package com.example.LiterAlura2.service;

public interface ITransformarDatosAClase {
    <T> T obtenerDatos(String json, Class<T> clase);
}
