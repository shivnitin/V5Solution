package com.vspl.myapplication.api

import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import com.vspl.freeelance.API.RetrofitResponse
import com.vspl.myapplication.ConnectionDetector
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiClient  {

    companion object {

        const val SERVER_URL = "https://movies-sample.herokuapp.com/"
        const val BASE_URL = SERVER_URL + "api/"
        var _conn : ConnectionDetector? =null;
        fun response(call: Call<String?>, currActivity: Activity, retrofitResponse: RetrofitResponse) {
             _conn = ConnectionDetector(currActivity)

            call.enqueue(object : Callback<String?> { override fun onResponse(call: Call<String?>, response: Response<String?>) {

                    try {
                        if (response.code() == 200) retrofitResponse.response(response.body()) else retrofitResponse.response(null)
                    } catch (e: Exception) {
                        retrofitResponse.response(null)
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {

                    retrofitResponse.response(null)
                }
            })
        }

        fun get(contex:Context):APIInterface {
                var cacheSize = 10 * 1024 * 1024 // 10 MB
               var cache = Cache(contex.cacheDir, cacheSize.toLong())
                val okHttpClient = OkHttpClient().newBuilder()
                    .addInterceptor(offlineInterceptor)
                    .addNetworkInterceptor(onlineInterceptor)
                    .cache(cache)
                    .addInterceptor(object : Interceptor {
                        @Throws(IOException::class)
                        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                            val originalRequest = chain.request()
                            val builder = originalRequest.newBuilder()
                                .header("X-Requested-With", "XMLHttpRequest")
                                .header("Content-Type", "application/x-www-form-urlencoded")
                            val newRequest = builder.build()
                            return chain.proceed(newRequest)
                        }
                    })
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build()
                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(okHttpClient)
                    .build().create(APIInterface::class.java)
            }

        var onlineInterceptor: Interceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val response = chain.proceed(chain.request())
                val maxAge =
                    60*10 // read from cache for 10 min. even if there is internet connection
                return response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .removeHeader("Pragma")
                    .build()
            }
        }
        var offlineInterceptor: Interceptor = object : Interceptor {

            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                var request = chain.request()
                if (!_conn!!.isConnectingToInternet) {
                    val maxStale = 60 * 10 // Offline cache available for 10 min.
                    request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .removeHeader("Pragma")
                        .build()
                }
                return chain.proceed(request)
            }
        }
    }
}