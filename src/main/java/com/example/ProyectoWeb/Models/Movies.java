package com.example.ProyectoWeb.Models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // = Auto_Increment
    private long id; 
    private String titulo; 
    private String director; 
    private String sinopsis; 
    private String genero; 
    private String formato; 
    private String idioma; 
    private String imagen; 
    private String clasificacion; 
    private LocalDate fechaEstreno; 
    private LocalDate fechaFinCartelera; 
    private int duracion; 
    private String distribuidora; 

    public Movies() {
    }

    public Movies(long id, String titulo, String director, String sinopsis, String genero, String formato,
            String idioma, String imagen, String clasificacion, LocalDate fechaEstreno, LocalDate fechaFinCartelera,
            int duracion, String distribuidora) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.sinopsis = sinopsis;
        this.genero = genero;
        this.formato = formato;
        this.idioma = idioma;
        this.imagen = imagen;
        this.clasificacion = clasificacion;
        this.fechaEstreno = fechaEstreno;
        this.fechaFinCartelera = fechaFinCartelera;
        this.duracion = duracion;
        this.distribuidora = distribuidora;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(LocalDate fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public LocalDate getFechaFinCartelera() {
        return fechaFinCartelera;
    }

    public void setFechaFinCartelera(LocalDate fechaFinCartelera) {
        this.fechaFinCartelera = fechaFinCartelera;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getDistribuidora() {
        return distribuidora;
    }

    public void setDistribuidora(String distribuidora) {
        this.distribuidora = distribuidora;
    }
}
