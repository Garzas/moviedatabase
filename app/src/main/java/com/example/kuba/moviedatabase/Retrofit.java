package com.example.kuba.moviedatabase;

import com.example.kuba.moviedatabase.Models.Search;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface Retrofit {

    //https://www.omdbapi.com/
    //s,type,y,r,callback,v

    @GET("/?r=json")
    Observable<Search> movieList(
            @Query("s") String name
    );

    @GET("/v1/search")
    Observable<Search> downloadMovies(
            @Query("s") String name,
            @Query("type") String type,
            @Query("limit") Integer limit,
            @Query("offset") Integer off
    );



}
