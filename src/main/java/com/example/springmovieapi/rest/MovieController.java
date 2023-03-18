package com.example.springmovieapi.rest;

import com.example.springmovieapi.domain.Movie;
import com.example.springmovieapi.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {

    private final Logger log = LoggerFactory.getLogger(MovieController.class);

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> findById(@PathVariable Long id) {
        log.info("REST request to find one movie");

        Optional<Movie> movieOpt = this.movieService.findById(id);

        // functional style
        return movieOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

        // not functional
        // if (movieOpt.isPresent())
           // return ResponseEntity.ok(movieOpt.get());
    }

    @GetMapping("/movies")
    public List<Movie> findAll() {
        log.info("REST request to find all movies");
        return movieService.findAll();
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        log.info("REST request to create a movie");
        if (movie.getId()!=null) {
            log.warn("Trying to create a new movie with existent id");
            return ResponseEntity.badRequest().build();
        }
         return ResponseEntity.ok(this.movieService.save(movie));
        }
}
