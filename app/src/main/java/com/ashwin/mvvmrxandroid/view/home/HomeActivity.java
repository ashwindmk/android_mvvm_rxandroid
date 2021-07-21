package com.ashwin.mvvmrxandroid.view.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ashwin.mvvmrxandroid.R;
import com.ashwin.mvvmrxandroid.view.Constant;
import com.ashwin.mvvmrxandroid.view.MyApplication;
import com.ashwin.mvvmrxandroid.view.MyListener;
import com.ashwin.mvvmrxandroid.view.next.NextActivity;

public class HomeActivity extends AppCompatActivity {
    // Anonymous inner Java class will leak memory
    MyListener listener = new MyListener() {
        @Override
        public void onReceive() {
            Log.d(Constant.DEBUG_TAG, "MainActivity: onReceive");
        }

        @Override
        public void onStop() {
            Log.d(Constant.DEBUG_TAG, "MainActivity: onStop");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((MyApplication) getApplication()).setListener(listener);

        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, NextActivity.class));
                finish();
            }
        });
    }
}