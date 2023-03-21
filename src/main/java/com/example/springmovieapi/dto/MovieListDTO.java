package com.example.springmovieapi.dto;

import com.example.springmovieapi.domain.Movie;

import java.util.List;

public class MovieListDTO {

    private List<Movie> movies;

    public MovieListDTO() {
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
