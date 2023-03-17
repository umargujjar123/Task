package com.example.basearchitectureproject.di

import com.example.basearchitectureproject.login.newtwork.LoginApi
import com.example.basearchitectureproject.util.UtilityValues
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkProvider {
    @Provides
    fun retrofitReturn(c: OkHttpClient, gson: Gson): Retrofit {


        return Retrofit.Builder()
            .baseUrl(UtilityValues.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(c)
            .build()

    }
    @Provides
    fun okHttpBuilder(): OkHttpClient {
        val i = HttpLoggingInterceptor()
        i.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(UtilityValues.TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(UtilityValues.TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(UtilityValues.TIME_OUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(i)
            .build()
    }

    @Provides
    fun gsonProvider(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }



    @Provides
    fun providerMyApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)

    }
}