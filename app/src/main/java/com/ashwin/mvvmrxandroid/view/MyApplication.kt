package com.ashwin.mvvmrxandroid.view

import android.app.Application

class MyApplication : Application() {
    var listener: MyListener? = null

    fun setMyListener(l: MyListener) {
        listener = l
    }
}