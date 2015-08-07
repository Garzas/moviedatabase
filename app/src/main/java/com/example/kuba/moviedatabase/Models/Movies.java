package com.example.kuba.moviedatabase.models;

public class Movies {
    private String Title;
    private String imdbID;
    private String Type;
    private Long Year;

    public Movies(String title, String imdbID, String type, Long year) {
        Title = title;
        this.imdbID = imdbID;
        Type = type;
        Year = year;
    }

    public String getTitle() {
        return Title;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return Type;
    }

    public Long getYear() {
        return Year;
    }
}
