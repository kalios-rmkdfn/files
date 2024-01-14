package com.servlet.modelo.entity;

import java.time.LocalDate;

public class Manual {
	
    private int id;
    private String titulo;
    private String categoria;
    private LocalDate fecha;
    private String ubicacion;
    private String imagen;
    private String resumen;
    private String articulo;

    // Constructor
    public Manual(int id, String titulo, String categoria, LocalDate fecha, String ubicacion, String imagen, String resumen, String articulo) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
        this.resumen = resumen;
        this.articulo = articulo;
    }

    // Getters y Setters para los atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }
}
