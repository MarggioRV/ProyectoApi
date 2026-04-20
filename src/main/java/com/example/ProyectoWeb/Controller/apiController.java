package com.example.ProyectoWeb.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

public class apiController {
    @RestController()
    public class DemoController {
        
        @GetMapping("/")
        public String HelloName(@RequestParam(required = false, defaultValue = "") String nombre) {
            return "¡Hola mundo " + nombre + "!";// Si no fuera false daría error al estar pidiendo ?nombre=algo
        }

        @GetMapping("/movies") 
        public String GetAllMovies() {
            return "<h1>¡Aquí tienes todas las pelis!</h1>"; 
        }

        @GetMapping("/movies/{id}")
        public String GetMoviesByID(@PathVariable("id") String id) {
            return "<h2>¡Aquí tienes el detalle del producto +" + id + "!</h2>";
        }

        @PostMapping("/movies")
        public String createProduct(@RequestParam String nombre, @RequestParam double precio) {
            return "Creando producto " + nombre + " de precio " + precio;
        }

        @PutMapping("/movies/{id}")
        public String actualizaProduct(@PathVariable String id) {
            return "Se ha modificado el cinta " + id;
        }

        @DeleteMapping("/movies/{id}")
        public String borraProduct(@PathVariable("id") String id) {
            return "Se eliminará la pelicula " + id;
        }

    }
}
