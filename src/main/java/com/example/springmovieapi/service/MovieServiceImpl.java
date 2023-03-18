package com.example.springmovieapi.service;

import com.example.springmovieapi.domain.Movie;
import com.example.springmovieapi.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService{

    private final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // spring repository methods

    @Override
    public List<Movie> findAll() {
        log.info("Executing findAll movies");
        return this.movieRepository.findAll();
    }

    @Override
    public Optional<Movie> findById(Long id) {
        log.info("Executing findById");
        return this.movieRepository.findById(id);
    }

    @Override
    public Long count() {
        log.info("Get total number of movies");
        return this.movieRepository.count();
    }

    @Override
    public Movie save(Movie movie) {
        log.info("Creating / Updating movie");

        // pre actions:
        if (!this.validateMovie(movie)) {
            return null;
        }

        // actions:
        // find template from DB
        Movie movieDB = this.movieRepository.save(movie);

        // post actions:
        // send notification
        // this.notificationService(NotificationType.CREATION, movie);

        return movieDB;
    }

    private boolean validateMovie(Movie movie) {
        if (movie == null) {
            log.warn("Trying to create a null movie");
            return false;
        }

        if (movie.hasNullFields(movie))
        {
            log.warn("Trying to create a movie with one or more null fields");
            return false;
        }

        if (movie.getRating() < 0 || movie.getRating() > 10) {
            log.warn("Trying to create a movie with an invalid rating");
            return false;
        }

        // year validation
        // other validations...

        return true;
    }

    @Override
    public void deleteAll() {
        log.info("Deleting all movies");
        this.movieRepository.deleteAll();
    }

    @Override
    public void deleteAll(List<Movie> movies) {
        log.info("Deleting the given list of movies");
        if (CollectionUtils.isEmpty(movies)) {
            log.warn("Trying to delete an empty or null movie list");
            return;
        }
        this.movieRepository.deleteAll(movies);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        log.info("Deleting movies by ID");
        if (CollectionUtils.isEmpty(ids)) {
            log.warn("Trying to delete an empty or null ID list");
            return;
        }
        this.movieRepository.deleteAllById(ids);
    }

    // custom methods

    @Override
    public List<Movie> findByGenre(String genre) {
        if (!StringUtils.hasLength(genre))
            return new ArrayList<>();
        return this.movieRepository.findByGenre(genre);
    }

    @Override
    public List<Movie> findByDirectorAndYear(String director, Integer year) {
        if (!StringUtils.hasLength(director) || year!=null)
            return new ArrayList<>();
        return this.movieRepository.findByDirectorAndYear(director, year);
    }

    @Override
    public List<Movie> findByTitleContaining(String title) {
        return this.movieRepository.findByTitleContaining(title);
    }

    @Override
    public List<Movie> findByDurationGreaterThanEqual(Duration duration) {
        if (duration==null || duration.isNegative() || duration.isZero())
            return new ArrayList<>();
        return this.movieRepository.findByDurationGreaterThanEqual(duration);
    }

    @Override
    public List<Movie> findByYearIn(List<Integer> years) {
        return this.movieRepository.findByYearIn(years);
    }

    @Override
    public List<Movie> findByYearBetween(Integer startYear, Integer endYear) {
        return this.movieRepository.findByYearBetween(startYear, endYear);
    }

    @Override
    public List<Movie> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate) {
        return this.movieRepository.findByReleaseDateBetween(startDate, endDate);
    }

    @Override
    public List<Movie> findByAvailableTrue() {
        return this.movieRepository.findByAvailableTrue();
    }

    @Override
    public Long deleteAllByAvailableFalse() {
        return this.movieRepository.deleteAllByAvailableFalse();
    }
}
