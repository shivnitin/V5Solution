package com.vspl.myapplication.api

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @get:GET("movies")
    val movieslist: Call<String?>?
}