package com.example.kuba.moviedatabase.Models;

public class MovieDetails {
    private String Title;
    private String Relased;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Poster;
    private String Type;
    private Double imdbRating;
    private Integer Year;

    public MovieDetails(String title, String relased, String genre, String director, String writer, String actors, String plot, String language, String poster, String type, Double imdbRating, Integer year) {
        Title = title;
        Relased = relased;
        Genre = genre;
        Director = director;
        Writer = writer;
        Actors = actors;
        Plot = plot;
        Language = language;
        Poster = poster;
        Type = type;
        this.imdbRating = imdbRating;
        Year = year;
    }

    public String getTitle() {
        return Title;
    }

    public String getRelased() {
        return Relased;
    }

    public String getGenre() {
        return Genre;
    }

    public String getDirector() {
        return Director;
    }

    public String getWriter() {
        return Writer;
    }

    public String getActors() {
        return Actors;
    }

    public String getPlot() {
        return Plot;
    }

    public String getLanguage() {
        return Language;
    }

    public String getPoster() {
        return Poster;
    }

    public String getType() {
        return Type;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public Integer getYear() {
        return Year;
    }
}
