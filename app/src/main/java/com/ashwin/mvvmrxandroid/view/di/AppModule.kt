package com.ashwin.mvvmrxandroid.view.di

import com.ashwin.mvvmrxandroid.data.di.DataModule
import com.ashwin.mvvmrxandroid.domain.repository.ColorRepository

object AppModule {
    fun provideColorRepository(): ColorRepository {
        return DataModule.getColorRepository()
    }
}
