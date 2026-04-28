package com.example.ProyectoWeb.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.example.ProyectoWeb.Models.Movies;
import com.example.ProyectoWeb.Services.ApiService;
import com.example.ProyectoWeb.Repository.MoviesRepository;

@RestController()
@RequestMapping("/movies") // Todas las rutas empiezan asi
public class apiController {

    @Autowired
    private ApiService service; // Conexion controlador-servicio

    @GetMapping("/")
    public String HelloName(@RequestParam(required = false, defaultValue = "") String nombre) {
        return "¡Hola mundo " + nombre + "!";// Si no fuera false daría error al estar pidiendo ?nombre=algo
    }

    // @GetMapping("/movies")
    // public String GetAllMovies() {
    // return "<h1>¡Aquí tienes todas las pelis!</h1>";
    // }

    @GetMapping
    public List<Movies> getAllMovies() {
        return service.listar(); // Devuelve lista real de la BD (JPA)
    }

    // @GetMapping("/movies/{id}")
    // public String getMoviesByID(@PathVariable("id") String id) {
    // return "<h2>¡Aquí tienes el detalle del producto +" + id + "!</h2>";
    // }

    @GetMapping("/{id}")
    public Movies getMovieById(@PathVariable long id) {
        return service.buscarPorId(id); // Ahora, trab con json y busca en la BD
    }

    // @PostMapping("/movies")
    // public String createProduct(@RequestParam String nombre, @RequestParam double
    // precio) {
    // return "Creando producto " + nombre + " de precio " + precio;
    // }

    @PostMapping
    public Movies crearMovie(@RequestBody Movies movie) {
        return service.crear(movie); // Se Guarda en BD
    }

    // @PutMapping("/movies/{id}")
    // public String actualizarProduct(@PathVariable String id) {
    // return "Se ha modificado el cinta " + id;
    // }

    @PutMapping("/{id}")
    public Movies actulizarMovie(@PathVariable long id, @RequestBody Movies movieActualizado) {
        return service.actualizar(id, movieActualizado); // Actualiza en BD
    }

    // @DeleteMapping("/movies/{id}")
    // public String borraProduct(@PathVariable("id") String id) {
    // return "Se eliminará la pelicula " + id;
    // }

    @DeleteMapping("/{id}")
    public String borrarMovie(@PathVariable long id) {
        return service.eliminar(id); // Elimina en BD
    }

    // Para proximas cintas
    @GetMapping("/proximamente")
    public List<Movies> getProximamente() {
        // Estaticamente, para pruebas
        LocalDate hoy = LocalDate.of(2026, 4, 27); // fecha estatica
        return service.listar().stream()
                // Filtrar por fecha de estreno mayor a hoy
                .filter(m -> m.getFechaEstreno().isAfter(hoy))
                .sorted((a, b) -> a.getFechaEstreno().compareTo(b.getFechaEstreno()))
                .toList();
    }

    // Para cintas en cartelera
    @GetMapping("/cartelera")
    public List<Movies> getEnCartelera() {
        // Estaticamente, para pruebas
        LocalDate hoy = LocalDate.of(2026, 4, 27);
        return service.listar().stream()
                .filter(m -> !m.getFechaFinCartelera().isBefore(hoy)) // no se ha ido de cartelera
                .filter(m -> !m.getFechaEstreno().isAfter(hoy)) // ya estrenada
                .toList();
    }

    // Buscar por título
    @GetMapping("/buscar")
    public List<Movies> buscarXTitulo(@RequestParam String titulo) {
        return service.buscarXTitulo(titulo); // Delega al servicio
    }

    // Endpoint de paginación
    // @GetMapping("/page")
    // public Page<Movies> getMoviesPaginadas(
    //         // Página actual (empieza en 0)
    //         @RequestParam(defaultValue = "0") int page,
    //         // Tamaño de página (12 por defecto)
    //         @RequestParam(defaultValue = "12") int size) {

    //     // Delega al servicio con paginación ordenada por ID
    //     return service.listarPaginado(page, size);
    // }
}
