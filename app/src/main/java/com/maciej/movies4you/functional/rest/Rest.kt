package com.maciej.movies4you.functional.rest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maciej.movies4you.functional.data.Constants
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Authenticator
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import javax.inject.Singleton



@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object Rest {


    private const val TOKEN_BEARER = "Bearer "

    private var defaultHttpClient = OkHttpClient.Builder()

    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRestInterface(retrofit: Retrofit): RestInterface {
        return retrofit.create(RestInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttp(authenticator: Authenticator): OkHttpClient? {
        defaultHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            var request = chain.request()
//            request =
//                request.newBuilder().addHeader("Authorization", TOKEN_BEARER + MyDatabase.authorizationDao.getKey())
//                    .build()
            chain.proceed(request)
        }
        defaultHttpClient.authenticator(authenticator)

        return defaultHttpClient.build()
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {

        val httpClient = OkHttpClient.Builder()
//        httpClient.retryOnConnectionFailure(false)
//        if (!MyDatabase.authorizationDao.getKey().isNullOrEmpty()) {
//            httpClient.addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                    .addHeader("Authorization", TOKEN_BEARER + MyDatabase.authorizationDao.getKey()).build()
//                chain.proceed(request)
//            }
//        }
//        httpClient.retryOnConnectionFailure(false)


        return Retrofit.Builder()
//            .client(httpClient.build())
            .baseUrl(Constants.Urls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            )
            .build()
    }

    val gson: Gson by lazy {
        GsonBuilder()
            .setPrettyPrinting()
            .create()
    }
}