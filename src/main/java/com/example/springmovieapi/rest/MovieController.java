package com.example.springmovieapi.rest;

import com.example.springmovieapi.entities.Movie;
import com.example.springmovieapi.dto.CountDTO;
import com.example.springmovieapi.dto.MovieListDTO;
import com.example.springmovieapi.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
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

    // SPRING CRUD METHODS

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

    @PutMapping("/movies")
    public ResponseEntity<Movie> update(@RequestBody Movie movie) {
        log.info("REST request to update a movie");
        if (movie.getId()!=null) {
            log.warn("Trying to update an existing movie without id");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.movieService.save(movie));
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Movie> delete(@PathVariable Long id) {
        log.info("REST request to delete an existing movie");
        this.movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/movies")
    public ResponseEntity<Movie> deleteAll() {
        log.info("REST request to delete all movies");
        this.movieService.deleteAll();
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/movies/count")
    public ResponseEntity<CountDTO> count() {
        log.info("REST request to count all movies");
        Long count = this.movieService.count();
        CountDTO dto = new CountDTO(count);
        dto.setMessage("Have a great day :)");
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/movies/deletemany")
    public ResponseEntity<Movie> deleteMany (@RequestBody MovieListDTO movieListDTO) {
        this.movieService.deleteAll(movieListDTO.getMovies());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/movies/deletemany/{ids}")
    public ResponseEntity<Movie> deleteMany (@PathVariable List<Long> ids) {
        this.movieService.deleteAllById(ids);
        return ResponseEntity.noContent().build();
    }

    // CUSTOM CRUD METHODS

    @GetMapping("/movies/director/{director}/year/{year}")
    public List<Movie> findByDirectorAndYear(@PathVariable String director, @PathVariable Integer year) {
        return this.movieService.findByDirectorAndYear(director, year);
    }

    @GetMapping("/movies/genres/{genre}")
    public List<Movie> findByGenre(@PathVariable String genre) {
        log.info("REST request to find movies by genre");
        return this.movieService.findByGenre(genre);
    }

    @GetMapping("/movies/duration-gte/{duration}")
    public List<Movie> findByDurationGreaterThanEqual (@PathVariable Duration duration) {
        return this.movieService.findByDurationGreaterThanEqual(duration);
    }
}
