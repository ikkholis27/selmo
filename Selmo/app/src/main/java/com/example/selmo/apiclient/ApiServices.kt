package com.example.selmo.apiclient

import com.example.selmo.model.Login
import com.example.selmo.model.Salmo
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @GET("login/cekloginapp")
    fun getAllTeam(@Query("username") username: String?,@Query ("password") password: String?): Call<Login>

    @FormUrlEncoded
    @POST("android")
    fun getData(@Field("api")  api: String, @Field ("uid") uid: String): Call<Salmo>
}
