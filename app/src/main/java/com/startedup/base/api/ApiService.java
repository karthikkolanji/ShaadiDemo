package com.startedup.base.api;

import com.startedup.base.model.shaadi.ShaadiUserResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

  @GET("api/")
  Observable<ShaadiUserResponse> getShaadiUsers(@Query("results") String results);
}
