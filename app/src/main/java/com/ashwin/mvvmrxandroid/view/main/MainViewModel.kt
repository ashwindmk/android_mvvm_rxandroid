package com.ashwin.mvvmrxandroid.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ashwin.mvvmrxandroid.domain.model.Color
import com.ashwin.mvvmrxandroid.domain.repository.ColorRepository
import com.ashwin.mvvmrxandroid.view.Constant
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.ResourceObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val colorRepository: ColorRepository) : ViewModel() {
    private val _colorList = MutableLiveData<List<Color>>()
    val colorList: LiveData<List<Color>> = _colorList

    val compositeDisposable = CompositeDisposable()

    fun loadColorList() {
        colorRepository.getColors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            //.observeOn(Schedulers.io())
            .subscribe(object : Observer<List<Color>> {
                override fun onSubscribe(d: Disposable) {
                    // start showing progress indicator
                    println("${Constant.DEBUG_TAG}: MainViewModel: getColors: onSubscribe [${Thread.currentThread().name}]")
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: getColors: onSubscribe [${Thread.currentThread().name}]")
                }

                override fun onNext(t: List<Color>) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: getColors: onNext [${Thread.currentThread().name}]")
                    _colorList.postValue(t)
                }

                override fun onError(e: Throwable) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: getColors: onError( ${e.message} ) [${Thread.currentThread().name}]")
                    _colorList.postValue(null)
                }

                override fun onComplete() {
                    // hide progress indicator
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: getColors: onComplete [${Thread.currentThread().name}]")
                }
            })
    }

    fun loadColor() {
        colorRepository.getColor()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Color> {
                override fun onSubscribe(d: Disposable) {
                    // start showing progress indicator
                    println("${Constant.DEBUG_TAG}: MainViewModel: getColor: onSubscribe [${Thread.currentThread().name}]")
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: getColor: onSubscribe [${Thread.currentThread().name}]")
                }

                override fun onNext(t: Color) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: getColor: onNext [${Thread.currentThread().name}]")
                }

                override fun onError(e: Throwable) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: getColor: onError( ${e.message} ) [${Thread.currentThread().name}]")
                    _colorList.postValue(null)
                }

                override fun onComplete() {
                    // hide progress indicator
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: getColor: onComplete [${Thread.currentThread().name}]")
                }
            })
    }

    fun postDisposableColor() {
        val color = Color(1024L, "uid1024", "red", "#FF0000")
        val disposable: Disposable = colorRepository.postColors(listOf(color, color))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.newThread())
            .subscribeWith(object : ResourceObserver<Boolean>() {
                override fun onNext(t: Boolean) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: loadDisposableColor: onNext [${Thread.currentThread().name}]")
                }

                override fun onError(e: Throwable) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: loadDisposableColor: onError [${Thread.currentThread().name}]")
                }

                override fun onComplete() {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: loadDisposableColor: onComplete [${Thread.currentThread().name}]")
                }
            })

        compositeDisposable.add(disposable)
    }

    fun postColors() {
        val color = Color(1024L, "uid1024", "red", "#FF0000")
        colorRepository.postColors(listOf(color, color))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.newThread())
            .subscribe(object : Observer<Boolean> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: postColors: onSubscribe [${Thread.currentThread().name}]")
                }

                override fun onNext(t: Boolean) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: postColors: onNext [${Thread.currentThread().name}]")
                }

                override fun onError(e: Throwable) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: postColors: onError [${Thread.currentThread().name}]")
                }

                override fun onComplete() {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: postColors: onComplete [${Thread.currentThread().name}]")
                }
            })
    }

    fun postColor() {
        val color = Color(1024L, "uid1024", "red", "#FF0000")
        colorRepository.postColor(color)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.newThread())
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: postColor: onSubscribe [${Thread.currentThread().name}]")
                }

                override fun onSuccess(t: Boolean) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: postColor: onSuccess [${Thread.currentThread().name}]")
                }

                override fun onError(e: Throwable) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: postColor: onError [${Thread.currentThread().name}]")
                }
            })
    }

    fun syncColors() {
        colorRepository.syncColors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: syncColors: onSubscribe [${Thread.currentThread().name}]")
                }

                override fun onComplete() {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: syncColors: onComplete [${Thread.currentThread().name}]")
                }

                override fun onError(e: Throwable) {
                    Log.d(Constant.DEBUG_TAG, "MainViewModel: syncColors: onError [${Thread.currentThread().name}]")
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
