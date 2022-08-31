package com.example.unsplash_app_practice.retrofit

import com.example.unsplash_app_practice.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    @GET(API.SEARCH_PHOTO)
    fun searchPhtos(@Query("query")searchTerm: String) : Call<JsonElement>

    @GET(API.SEARCH_USERS)
    fun searchUsers(@Query("query")searchTerm: String) : Call<JsonElement>
}