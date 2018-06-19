package com.startedup.base.api;

import com.startedup.base.model.movies.TopRatedMovieResponse;

import com.startedup.base.model.shaadi.ShaadiResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

  @GET("movie/top_rated")
  Observable<TopRatedMovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);


  @GET("api/")
  Observable<ShaadiResponse> getShaadiResults(@Query("results") String results );
}
