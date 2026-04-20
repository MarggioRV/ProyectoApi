package com.example.ProyectoWeb.Models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Movies {
    @Id
    private long id;
    private String titulo;
    private String director;
    private String sinopsis;
    private String genero;
    private String formato;
    private String idioma;
    private String imagen;
    private String Clasificacion;
    private LocalDate fechaEstreno;
    private LocalDate fechaFinCartelera;

    public Movies() {
    }

    public Movies(long id, String titulo, String director, String sinopsis, String genero, String formato,
            String idioma, String imagen, String Clasificacion, LocalDate fechaEstreno, LocalDate fechaFinCartelera) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.sinopsis = sinopsis;
        this.genero = genero;
        this.formato = formato;
        this.idioma = idioma;
        this.imagen = imagen;
        this.Clasificacion = Clasificacion;
        this.fechaEstreno = fechaEstreno;
        this.fechaFinCartelera = fechaFinCartelera;
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

    public String getClasificacion() {
        return Clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        Clasificacion = clasificacion;
    }
}
