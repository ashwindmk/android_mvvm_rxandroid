package com.ashwin.mvvmrxandroid.domain.repository

import com.ashwin.mvvmrxandroid.domain.model.Color
import io.reactivex.*

interface ColorRepository {
    fun getColors(): Observable<List<Color>>
    fun getColor(): Observable<Color>
    fun postColors(colors: List<Color>): Observable<Boolean>
    fun postColor(color: Color): Single<Boolean>
    fun tryColor(color: Color?): Maybe<Boolean>
    fun syncColors(): Completable
}
