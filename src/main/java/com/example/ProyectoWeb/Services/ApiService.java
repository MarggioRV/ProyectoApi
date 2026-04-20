package com.example.ProyectoWeb.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoWeb.Repository.MoviesRepository;
import com.example.ProyectoWeb.Models.Movies;

@Service
public class ApiService {
    @Autowired
    private MoviesRepository repository;

    // CREATE
    public Movies crear(Movies movie) {
        return repository.save(movie); // JPA asigna ID automáticamente
    }

    // READ - obtener todos
    public List<Movies> listar() {
        return repository.findAll();
    }

    // READ - obtener por id
    public Movies buscarPorId(long id) {
        return repository.findById(id).orElse(null);
    }

    // UPDATE
    public Movies actualizar(long id, Movies movieActualizado) {
        Movies existente = repository.findById(id).orElse(null);

        if (existente != null) {

            existente.setTitulo(movieActualizado.getTitulo());
            existente.setDirector(movieActualizado.getDirector());
            existente.setSinopsis(movieActualizado.getSinopsis());
            existente.setGenero(movieActualizado.getGenero());
            existente.setFormato(movieActualizado.getFormato());
            existente.setIdioma(movieActualizado.getIdioma());
            existente.setImagen(movieActualizado.getImagen());
            existente.setClasificacion(movieActualizado.getClasificacion());
            existente.setFechaEstreno(movieActualizado.getFechaEstreno());

            return repository.save(existente);
        }
        return null; // no existe
    }

    // DELETE
    public String eliminar(long id) {
        Movies existente = repository.findById(id).orElse(null);
        if (existente != null) {
            repository.deleteById(id);
            return "Movie " + id + " eliminada";

        }
        return "Movie " + id + " no encontrada";
    }
}