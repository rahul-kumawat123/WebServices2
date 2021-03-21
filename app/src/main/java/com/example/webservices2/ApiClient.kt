package com.example.webservices2

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL ="https://storage.googleapis.com/network-security-conf-codelab.appspot.com/"
    private const val cacheSize = (5 * 1024 * 1024).toLong()
    private const val HEADER_CACHE_CONTROL = "Cache-Control"
    private const val HEADER_PRAGMA = "Pragma"

    val getClient : ApiInterface
        get() {

            val httpClient = OkHttpClient.Builder()
                    .cache(cache())
                    .addInterceptor(httpLoggingInterceptor())
                    .addInterceptor(offlineInterceptor())
                    .addInterceptor(networkInterceptor())
                    .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()

            return retrofit.create(ApiInterface::class.java)

        }



    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun cache(): Cache {

        return Cache(MyApplication.myApplicationInstance?.cacheDir, cacheSize)

    }


    private fun httpLoggingInterceptor(): Interceptor{

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }


    private fun offlineInterceptor(): Interceptor {

        return Interceptor { chain ->
            var request = chain.request()

            // prevent caching when network is on. For that we use the "networkInterceptor"
            if (MyApplication().hasNetwork() == false){
                val cacheControl = CacheControl.Builder()
                        .maxStale(7,TimeUnit.DAYS)
                        .build()

                request = request.newBuilder()
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .removeHeader(HEADER_PRAGMA)
                        .cacheControl(cacheControl )
                        .build()
            }
            chain.proceed(request)
        }
    }


    private fun networkInterceptor(): Interceptor {

        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                    .maxAge(5,TimeUnit.SECONDS)
                    .build()

            response.newBuilder()
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .removeHeader(HEADER_PRAGMA)
                    .build()

        }
    }
}
