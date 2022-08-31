package com.example.unsplash_app_practice.retrofit

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.unsplash_app_practice.App
import com.example.unsplash_app_practice.utils.API
import com.example.unsplash_app_practice.utils.Constants.TAG
import com.example.unsplash_app_practice.utils.isJsonArray
import com.example.unsplash_app_practice.utils.isJsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofitClient: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit?{

        //로깅 인터셉터
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d(TAG,"로깅 인터 : $message")
                when{
                    message.isJsonObject() ->
                        Log.d(TAG,JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG,JSONObject(message).toString(4))
                    else -> {
                        try {
                            Log.d(TAG,JSONObject(message).toString(4))
                        }catch (e:Exception){
                            Log.d(TAG,message)
                        }
                    }

                }
            }

        })
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        val baseParameterInterceptor : Interceptor = (object : Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d(TAG,"intercepter 호출")
                val originalRequest = chain.request()
                val addedUrl = originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID).build()
                val finalRequest = originalRequest.newBuilder()
                    .url(addedUrl)
                    .method(originalRequest.method, originalRequest.body)
                    .build()
                val response = chain.proceed(finalRequest)
                if (response.code != 200){
                    Handler(Looper.getMainLooper()).post{
                        Toast.makeText(App.instance,"${response.code}",Toast.LENGTH_SHORT).show()
                    }
                }
                return response
            }

        })

        client.addInterceptor(baseParameterInterceptor)
        client.connectTimeout(10,TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10,TimeUnit.SECONDS)
        client.retryOnConnectionFailure(true)


        Log.d(TAG,"레트로핏 called")
        if (retrofitClient == null){
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}