package com.example.ProyectoWeb.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProyectoWeb.Models.Movies;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {
    
}
