package com.example.springmovieapi.repository;

import com.example.springmovieapi.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

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
