package com.example.unsplash_app_practice.retrofit

import android.util.Log
import com.example.unsplash_app_practice.model.Photo
import com.example.unsplash_app_practice.utils.API
import com.example.unsplash_app_practice.utils.Constants.TAG
import com.example.unsplash_app_practice.utils.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

class RetrofitManager {
    companion object{
        val instance = RetrofitManager()
    }

    private val iRetrofit : IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE,ArrayList<Photo>?) -> Unit){
        val term = searchTerm ?: ""
        val call= iRetrofit?.searchPhtos(searchTerm = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG,"성공 : ${response.body()}")
                when(response.code()){
                    200 -> {
                        response.body()?.let {
                            var parsedPhotoDataArray = ArrayList<Photo>()
                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("results")
                            val total = body.get("total").asInt

                            if(total == 0){
                                completion(RESPONSE_STATE.NO_CONTENT, null)
                            }else{
                                results.forEach { resultItem ->
                                    val resultItemObject = resultItem.asJsonObject
                                    val user = resultItemObject.get("user").asJsonObject
                                    val username: String = user.get("username").asString
                                    val likecount = resultItemObject.get("likes").asInt
                                    val thumbnailLink = resultItemObject.get("urls").asJsonObject.get("thumb").asString
                                    val createdAt = resultItemObject.get("created_at").asString
                                    var parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                    val formatter = SimpleDateFormat("yyyy년\nMM월 dd일")
                                    val outputDateString = formatter.format(parser.parse(createdAt))
                                    val photoItem = Photo(author = username, likesCount = likecount, thumnail = thumbnailLink, createdAt = outputDateString)

                                    parsedPhotoDataArray.add(photoItem)
                                }
                                completion(RESPONSE_STATE.OKAY,parsedPhotoDataArray)
                            }
                        }

                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG,"실패 : $t")
                completion(RESPONSE_STATE.FAIL, null)
            }

        })
    }
}