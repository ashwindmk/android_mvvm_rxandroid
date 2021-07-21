package com.ashwin.mvvmrxandroid.data.di

import com.ashwin.mvvmrxandroid.data.repository.ColorRepositoryImpl
import com.ashwin.mvvmrxandroid.data.source.ColorApi
import com.ashwin.mvvmrxandroid.data.source.network.RetrofitColorApi
import com.ashwin.mvvmrxandroid.domain.repository.ColorRepository
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {
    val baseURL = "https://random-data-api.com/"

    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun getColorApi(retrofit: Retrofit): ColorApi {
        return retrofit.create(RetrofitColorApi::class.java)
    }

    fun getColorRepository(): ColorRepository {
        return ColorRepositoryImpl(getColorApi(getRetrofit()))
    }
}
