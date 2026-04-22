package com.example.ProyectoWeb.Services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;

import javax.imageio.ImageIO;

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

    // === VALIDACIONES ===\\

    // VALIDACION_CAMPOS+
    public void camposBasicos(Movies movie) {
        if (movie == null) {
            throw new IllegalArgumentException("La cinta debe existir");
        }

        // Llamada Aux
        checkTexto(movie.getTitulo(), "Falta titulo");
        checkTexto(movie.getDirector(), "Tiene que existir un director");
        checkTexto(movie.getGenero(), "Debe poseer un genero");
        checkTexto(movie.getClasificacion(), "Sí o sí, tiene que tener una clasificacion");

        // Fecha de estreno
        fechaNotNULL(movie.getFechaEstreno(), "Debe tener una fecha de estreno");
        fechaNotNULL(movie.getFechaFinCartelera(), "Debe tener una fecha de fin de cartelera");

        // Coherencia_fechas
        if (movie.getFechaEstreno().isAfter(movie.getFechaFinCartelera())) {
            throw new IllegalArgumentException(
                    "La fecha de estreno no puede ser posterior al fin de cartelera, respecto al ID: " + movie.getId());
        }

        // Duracion
        checkPositivo(movie.getDuracion(), "Duracion no valida...");
    }

    // FUNCIONES AUX: Validacion_CamposB

    // En caso de vacio
    public void checkTexto(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    // En caso de numero negativo o cero
    public void checkPositivo(int valor, String mensaje) {
        if (valor <= 0) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    public void fechaNotNULL(Object valor, String mensaje) {
        if (valor == null) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    // VALIDACION_ID
    public String validarID(long id) {
        if (id <= 0) {
            return "ID no valido";
        }
        return null;
    }

    // CONSTANTE DEFAULT
    final String DEFAULT_IMG = "/Images/Movie-Icon.png";

    // VALIDACION_IMAGEN
    public String checkImagen(String url) {
        if (url == null || url.isBlank()) {
            return DEFAULT_IMG;
        }

        if (esRutaLocal(url) || esGif(url)) {
            return DEFAULT_IMG;
        }

        if (esHttp(url)) {
            return urlValido(url);
        }

        if (extensionValida(url)) {
            return url;
        }

        return DEFAULT_IMG;
    }

    // FUNCIONES AUX: Validacion_Imagen

    // Checkeo de la url
    public String urlValido(String url) {
        try {
            URLConnection conn = URI.create(url).toURL().openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            String tipo = conn.getContentType();

            if (tipo == null || !tipo.startsWith("image/")) {
                return DEFAULT_IMG;
            }

            try (InputStream in = conn.getInputStream()) {
                if (ImageIO.read(in) == null) {
                    return DEFAULT_IMG;
                }
            }
            return url;

        } catch (IOException | IllegalArgumentException e) {
            return DEFAULT_IMG;
        }
    }

    // Demás Booleans

    private boolean esRutaLocal(String url) {
        return url.startsWith("file:") || url.contains("\\");
    }

    private boolean esGif(String url) {
        return url.toLowerCase().endsWith(".gif");
    }

    private boolean esHttp(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    private boolean extensionValida(String url) {
        String lower = url.toLowerCase();
        return lower.endsWith(".png")
            || lower.endsWith(".jpg")
            || lower.endsWith(".jpeg");
    }
}