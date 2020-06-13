package com.vspl.myapplication

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class MovieModel :Serializable{

    @SerializedName("id")
    @Expose
     var id: Int = 0
    @SerializedName("title")
    @Expose
     var title: String = ""
    @SerializedName("year")
    @Expose
     var year: String = ""
    @SerializedName("genre")
    @Expose
     var genre: String = ""
    @SerializedName("poster")
    @Expose
     var poster: String = ""



}