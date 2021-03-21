package com.example.webservices2

import retrofit2.Call
import retrofit2.http.GET
import javax.security.auth.callback.Callback

interface ApiInterface {

    //end point

    @GET("v2/posts.json")
    fun getData(): Call<ResponseDataModel>
}