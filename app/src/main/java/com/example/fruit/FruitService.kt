package com.example.fruit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FruitService {
    @GET("api/fruit/all")
    fun getFruit(): Call<List<FruitInfo>>
}