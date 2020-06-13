package com.vspl.myapplication.api;




import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {


    @GET("movies")
    Call<String> getMovieslist();



}
