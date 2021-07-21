package com.ashwin.mvvmrxandroid.data.source

import com.ashwin.mvvmrxandroid.domain.model.Color
import io.reactivex.Observable

interface ColorApi {
    fun getColors(): Observable<List<Color>>
}
