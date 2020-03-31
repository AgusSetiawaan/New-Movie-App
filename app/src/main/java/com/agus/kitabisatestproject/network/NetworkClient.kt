package com.agus.kitabisatestproject.network

import com.agus.kitabisatestproject.BuildConfig
import com.agus.kitabisatestproject.util.Constant.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {

    private var service: MovieService? = null

    @JvmStatic
    fun getService(): MovieService {
        if (service == null) {
            val builder = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

            val okHttpClientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okHttpClientBuilder.addInterceptor(loggingInterceptor)
            }
            val okHttpClient = okHttpClientBuilder
                .readTimeout(25, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .build()
                    )
                }
                .build()
            val retrofit = builder.baseUrl(BASE_URL).client(okHttpClient).build()
            service = retrofit.create(MovieService::class.java)
        }
        return service!!
    }

}