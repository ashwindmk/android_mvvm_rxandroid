package com.ashwin.mvvmrxandroid.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashwin.mvvmrxandroid.R
import com.ashwin.mvvmrxandroid.domain.model.Color
import com.ashwin.mvvmrxandroid.view.Constant
import com.ashwin.mvvmrxandroid.view.MyListener
import com.ashwin.mvvmrxandroid.view.di.AppModule
import com.ashwin.mvvmrxandroid.view.home.HomeActivity
import com.ashwin.mvvmrxandroid.view.next.NextActivity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var colorListAdapter: ColorListAdapter
    lateinit var nextButton: Button
    var count: Int = 0

    // This is anonymous inner class: this will leak memory.
    inner class MyListenerImpl : MyListener {
        override fun onReceive() {
            Log.d(Constant.DEBUG_TAG, "Lis: onReceive")
        }

        override fun onStop() {
            Log.d(Constant.DEBUG_TAG, "Lis: onStop")
        }
    }

    // Kotlin will convert this to a static inner class if this is non-instance-capture, will not leak memory.
    val listener = object : MyListener {
        override fun onReceive() {
            Log.d(Constant.DEBUG_TAG, "MainActivity: onReceive")
            //count++  // instance-capture: will leak memory
        }

        override fun onStop() {
            Log.d(Constant.DEBUG_TAG, "MainActivity: onStop")
        }
    }

    val observer = object : Observer<Color> {
        override fun onSubscribe(d: Disposable) {
            // start showing progress indicator
            Log.d(Constant.DEBUG_TAG, "MainViewModel: getColor: onSubscribe [${Thread.currentThread().name}]")
        }

        override fun onNext(t: Color) {
            Log.d(Constant.DEBUG_TAG, "MainViewModel: getColor: onNext [${Thread.currentThread().name}]")
            nextButton.isEnabled = true
        }

        override fun onError(e: Throwable) {
            Log.d(Constant.DEBUG_TAG, "MainViewModel: getColor: onError( ${e.message} ) [${Thread.currentThread().name}]")
        }

        override fun onComplete() {
            // hide progress indicator
            Log.d(Constant.DEBUG_TAG, "MainViewModel: getColor: onComplete [${Thread.currentThread().name}]")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //(application as MyApp).setMyListener(listener)
        //(application as MyApp).setMyListener(MyListenerImpl())

        val colorRepository = AppModule.provideColorRepository()
        mainViewModel = ViewModelProvider(this, MainModule(colorRepository)).get(MainViewModel::class.java)
        colorListAdapter = ColorListAdapter()

        val homeButton = findViewById<Button>(R.id.home_button)
        homeButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        }

        nextButton = findViewById<Button>(R.id.next_button)
        nextButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, NextActivity::class.java))
            finish()
        }

        val postButton = findViewById<Button>(R.id.post_button)
        postButton.setOnClickListener {
            //mainViewModel.postColor()
            //mainViewModel.loadColor()
            colorRepository.getColor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        }

        val colorRecyclerView = findViewById<RecyclerView>(R.id.color_recycler_view)
        colorRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(applicationContext, LinearLayout.VERTICAL))
            adapter = colorListAdapter
        }

        mainViewModel.colorList.observe(this, {
            Log.d(Constant.DEBUG_TAG, "colorList.onChange( $it )")
            if (it != null) {
                colorListAdapter.list = it
                colorListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })

        mainViewModel.loadColorList()
    }
}
