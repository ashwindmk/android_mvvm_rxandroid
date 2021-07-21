package com.ashwin.mvvmrxandroid.data.source.network

import com.ashwin.mvvmrxandroid.data.source.ColorApi
import com.ashwin.mvvmrxandroid.domain.model.Color
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitColorApi : ColorApi {
    @GET("api/color/random_color?size=5")
    override fun getColors(): Observable<List<Color>>
}
