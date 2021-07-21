package com.ashwin.mvvmrxandroid.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashwin.mvvmrxandroid.domain.repository.ColorRepository
import java.lang.IllegalArgumentException

class MainModule(private val colorRepository: ColorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(colorRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}
