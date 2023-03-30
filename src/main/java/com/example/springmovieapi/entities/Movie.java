package com.example.springmovieapi.entities;

import com.example.springmovieapi.service.DurationConverter;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

@Entity //maps Movie objects to a DB table
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String director;
    private String genre;
    private Float rating;
    @Convert(converter = DurationConverter.class)
    private Duration duration;
    private Integer year;
    private LocalDate releaseDate;
    private Boolean available;

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public boolean hasNullFields(Movie movie) {
        return Stream.of(movie.title, movie.director, movie.genre, movie.rating,
                        movie.duration, movie.year, movie.releaseDate, movie.available)
                .anyMatch(Objects::isNull);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", duration=" + duration +
                ", year=" + year +
                ", releaseDate=" + releaseDate +
                ", available=" + available +
                '}';
    }
}
