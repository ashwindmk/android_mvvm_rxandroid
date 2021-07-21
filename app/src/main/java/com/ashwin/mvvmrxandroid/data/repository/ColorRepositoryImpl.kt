package com.ashwin.mvvmrxandroid.data.repository

import android.accounts.NetworkErrorException
import android.util.Log
import com.ashwin.mvvmrxandroid.data.source.ColorApi
import com.ashwin.mvvmrxandroid.domain.model.Color
import com.ashwin.mvvmrxandroid.domain.repository.ColorRepository
import com.ashwin.mvvmrxandroid.view.Constant
import io.reactivex.*

class ColorRepositoryImpl(private val colorApi: ColorApi) : ColorRepository {
    val TAG = ColorRepositoryImpl::class.java.simpleName

    override fun getColors(): Observable<List<Color>> {
        return colorApi.getColors()
    }

    override fun postColors(colors: List<Color>): Observable<Boolean> {
        Log.d(Constant.DEBUG_TAG, "$TAG: postColors( $colors ) [${Thread.currentThread().name}]")
        //return Observable.just(true)
        return Observable.create(object : ObservableOnSubscribe<Boolean> {
            override fun subscribe(emitter: ObservableEmitter<Boolean>) {
                Log.d(Constant.DEBUG_TAG, "$TAG: postColor( $colors ) subscribe: [${Thread.currentThread().name}]")
                emitter.onNext(true)
                emitter.onNext(true)
                emitter.onComplete()
            }
        })
    }

    override fun getColor(): Observable<Color> {
        return Observable.create(object : ObservableOnSubscribe<Color> {
            override fun subscribe(emitter: ObservableEmitter<Color>) {
                Log.d(Constant.DEBUG_TAG, "$TAG: postColor: ObservableOnSubscribe.subscribe [${Thread.currentThread().name}]")
                Thread.sleep(20_000L)
                val color = Color(1024L, "uid1024", "red", "#FF0000")
                emitter.onNext(color)

                //emitter.onComplete()
                Thread.sleep(10_000L)
                Log.d(Constant.DEBUG_TAG, "$TAG: postColor: ObservableOnSubscribe.subscribe isDisposed: ${emitter.isDisposed}")

                Thread.sleep(10 * 60_000L)
            }
        })
    }

    override fun postColor(color: Color): Single<Boolean> {
        // return Single.just(true)
        return Single.create(object : SingleOnSubscribe<Boolean> {
            override fun subscribe(emitter: SingleEmitter<Boolean>) {
                Log.d(Constant.DEBUG_TAG, "$TAG: postColor: SingleOnSubscribe.subscribe [${Thread.currentThread().name}]")
                Thread.sleep(20_000L)
                val code = 200
                if (code == 200) {
                    emitter.onSuccess(true)
                } else if (code <= 500) {
                    emitter.onSuccess(false)
                } else {
                    emitter.onError(NetworkErrorException())
                }
            }
        })
    }

    override fun tryColor(color: Color?): Maybe<Boolean> {
        // return Maybe.just(true)
        // return Maybe.empty()
        return Maybe.create(object : MaybeOnSubscribe<Boolean> {
            override fun subscribe(emitter: MaybeEmitter<Boolean>) {
                if (color == null) {
                    emitter.onComplete()
                } else {
                    val code = 200
                    if (code == 200) {
                        emitter.onSuccess(true)
                    } else if (code <= 500) {
                        emitter.onSuccess(false)
                    } else {
                        emitter.onError(NetworkErrorException())
                    }
                }
            }
        })
    }

    override fun syncColors(): Completable {
        /*return Completable.fromSingle(Single.create(object : SingleOnSubscribe<Boolean> {
            override fun subscribe(emitter: SingleEmitter<Boolean>) {
                // Sync local cache with remote
                emitter.onSuccess(true)
            }
        }))*/

        return Completable.create(object : CompletableOnSubscribe {
            override fun subscribe(emitter: CompletableEmitter) {
                Log.d(Constant.DEBUG_TAG, "$TAG: syncColors: CompletableOnSubscribe.subscribe [${Thread.currentThread().name}]")
                Thread.sleep(5000L)
                emitter.onComplete()


            }
        })
    }
}
