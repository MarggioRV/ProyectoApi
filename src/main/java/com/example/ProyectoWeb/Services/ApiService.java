package com.example.ProyectoWeb.Services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.ProyectoWeb.Repository.MoviesRepository;
import com.example.ProyectoWeb.Models.Movies;

@Service
public class ApiService {
    @Autowired
    private MoviesRepository repository;

    // CREATE
    public Movies crear(Movies movie) {

        // LLamada a validacion
        camposBasicos(movie);

        // Valores por defecto
        movie.setClasificacion(defaultCla(movie.getClasificacion()));
        movie.setGenero(defaultGe(movie.getGenero()));
        movie.setIdioma(defaultIdioma(movie.getIdioma()));
        movie.setFormato(defaultFor(movie.getFormato()));
        movie.setSinopsis(defaultSinop(movie.getSinopsis()));
        movie.setDistribuidora(defaultDis(movie.getDistribuidora()));

        // LLamada a validacion_imagen
        movie.setImagen(checkImagen(movie.getImagen()));

        // Save en la BD
        return repository.save(movie); // JPA asigna ID automáticamente
    }

    // READ - obtener todos
    public List<Movies> listar() {

        // Se obtiene una l de todas las cintas de la BD
        List<Movies> lista = repository.findAll();

        // LLamada para corregir imgs
        lista.forEach(m -> m.setImagen(checkImagen(m.getImagen())));

        return lista;
    }

    // READ - obtener por id
    public Movies buscarPorId(long id) {

        // Llamada para validar ID
        validarID(id);

        // Buscar pelicula en BD
        Movies movie = repository.findById(id).orElse(null);

        // Llamada y disyuncion para corregir
        if (movie != null) {
            movie.setImagen(checkImagen(movie.getImagen()));
        }

        return movie;
    }

    // public Movies buscarPorId(long id) {
    // return repository.findById(id).orElse(null);
    // }

    // UPDATE
    public Movies actualizar(long id, Movies movieActualizado) {

        validarID(id);

        // Buscar pelicula existente
        Movies existente = repository.findById(id).orElse(null);

        if (existente != null) {

            // Llamada a validacion de campos basicos
            camposBasicos(movieActualizado);

            // Llamada a validacion de imagen
            movieActualizado.setImagen(checkImagen(movieActualizado.getImagen()));

            // Pasando datos validos
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

        validarID(id);

        // Buscar pelicula existente
        Movies existente = repository.findById(id).orElse(null);

        // Si existe, eliminar y retornar mensaje
        if (existente != null) {
            repository.deleteById(id);
            return "Movie " + id + " eliminada";

        }
        // Si no existe, retornar mensaje de no encontrado
        return "Movie " + id + " no encontrada";
    }

    // === BUSCADORES ESPECIALES ===\\

    // Bscar por título
    public List<Movies> buscarXTitulo(String titulo) {

        // Obtener todas las películas de la BD
        List<Movies> todas = repository.findAll();

        // Filtrar por título (case-insensitive, contains)
        return todas.stream()
                .filter(m -> m.getTitulo()
                        .toLowerCase()
                        .contains(titulo.toLowerCase()))
                .toList();
    }

    // === Pag ===\\
    // public Page<Movies> listarPaginado(int page, int size) {
    //     // Delega al repositorio con paginación ordenada por ID
    //     return repository.findAll(PageRequest.of(page, size, Sort.by("id")));
    // }

    // === VALIDACIONES (Private) ===\\

    // VALIDACION_CAMPOS+
    private void camposBasicos(Movies movie) {
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
    private void checkTexto(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    // En caso de numero negativo o cero
    private void checkPositivo(int valor, String mensaje) {
        if (valor <= 0) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    // En caso de fecha vacia (Db a Date)
    private void fechaNotNULL(Object valor, String mensaje) {
        if (valor == null) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    // VALIDACION_ID
    private void validarID(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID no válido");
        }
    }

    // CONSTANTE DEFAULT
    final String DEFAULT_IMG = "/Images/Movie-Icon.png";

    // VALIDACION_IMAGEN
    private String checkImagen(String url) {
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
    private String urlValido(String url) {
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

    // === OTROS DEFAULTS ===\\

    // Clasificación
    private String defaultCla(String valor) {
        return (valor == null || valor.isBlank()) ? "Sin clasificar" : valor;
    }

    // Género
    private String defaultGe(String valor) {
        return (valor == null || valor.isBlank()) ? "Desconocido" : valor;
    }

    // Idioma
    private String defaultIdioma(String valor) {
        return (valor == null || valor.isBlank()) ? "N/A" : valor;
    }

    // Formato por defecto
    private String defaultFor(String valor) {
        return (valor == null || valor.isBlank()) ? "N/A" : valor;
    }

    // Sinopsis
    private String defaultSinop(String valor) {
        return (valor == null || valor.isBlank()) ? "Descripción no disponible" : valor;
    }

    // Distribuidora
    private String defaultDis(String valor) {
        return (valor == null || valor.isBlank()) ? "Dominio Publico" : valor;
    }
}