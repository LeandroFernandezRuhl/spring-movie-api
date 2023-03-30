package com.example.springmovieapi.service;

import com.example.springmovieapi.entities.Movie;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieService {

    // spring repository methods

    List<Movie> findAll();

    Optional<Movie> findById(Long id);

    Long count();

    Movie save(Movie movie);

    void deleteById(Long id);

    void deleteAll();

    void deleteAll(List<Movie> movies);

    void deleteAllById(List<Long> id);

    // custom methods

    List<Movie> findByGenre(String genre);

    List<Movie> findByDirectorAndYear(String director, Integer year);

    List<Movie> findByTitleContaining(String title);

    List<Movie> findByDurationGreaterThanEqual(Duration duration);

    List<Movie> findByYearIn(List<Integer> years);

    List<Movie> findByYearBetween(Integer startYear, Integer endYear);

    List<Movie> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);

    List<Movie> findByAvailableTrue();

    Long deleteAllByAvailableFalse();
}
